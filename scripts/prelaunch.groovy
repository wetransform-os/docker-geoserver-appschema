#!/usr/bin/env groovy
import groovy.xml.*

// definitions
basePath = new File(args[0])

def setDefaultPasswords() {
  // Password is expected to be in the pattern expected by GeoServer
  // e.g. "digest1:base64encodedPasswdAndSalt"
  def masterPasswd = System.env.GS_MASTER_PASSWORD
  def masterFile = new File(basePath, 'data/security/masterpw.digest')
  if (masterPasswd) {
    if (masterPasswd.startsWith('digest1:')) {
      masterFile.text = masterPasswd
      println "Master password set"
    }
    else {
      println 'Unrecognized/unsupported master password pattern'
    }
  }

  // Password is expected to be in the pattern expected by GeoServer
  // with either "digest1:..." or "plain:..." or as a plain password
  def adminPasswd = System.env.GS_ADMIN_PASSWORD
  def usersFile = new File(basePath, 'data/security/usergroup/default/users.xml')
  if (adminPasswd) {
    if (!adminPasswd.startsWith('plain:') || !adminPasswd.startsWith('digest1:')) {
      // assuming plain password
      adminPasswd = "plain:$adminPasswd"
      println 'Setting plain admin password'
    }
    else {
      println 'Setting configured pattern as admin password'
    }

    def parser = new XmlParser(false, true)
    def userRegistry = parser.parseText(usersFile.text)

    boolean updatedUser = false
    def adminUser = userRegistry.users.user.each { user ->
      if (user.@name == 'admin') {
        user.@password = adminPasswd
        updatedUser = true
      }
    }

    if (!updatedUser) {
      def users = userRegistry.users[0] ?: parser.createNode(userRegistry, new QName('users'), [:])
      parser.createNode(
        users, new QName('user'), [enabled: 'true', name: 'admin', password: adminPasswd]
      )
    }

    usersFile.text = XmlUtil.serialize(userRegistry)
  }
}

// actions
setDefaultPasswords()
