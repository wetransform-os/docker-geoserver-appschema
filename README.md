# docker-geoserver-appschema
Simple Dockerfile to build an image running GeoServer (plus app-schema extension) on Tomcat 6 and JRE 7.

## Configuration from host

Example of mounting a GeoServer data directory present on the Docker host:

```
docker run -it -p 8080:8080 -v /data/dir/on/host:/opt/webapps/geoserver/data wetransform/geoserver-appschema
```

## Environment variables

`GS_ADMIN_PASSWORD`
`GS_MASTER_PASSWORD`

These variables can be used to conveniently set administrator and master passwords if you do not use a custom data directory but don't want to use the default passwords (e.g. in publicly accessible test environments). Passwords are expected to provided as

* GeoServer digest encrypted password (`digest1:hashedPasswordAndSalt`) **recommended**
* GeoServer plain text password (`plain:password`)
* or simply the plain password

If an admin password is provided but no master password, the admin password is used as the master password as well.

Example call:

```
docker run -it -p 8080:8080 -e GS_ADMIN_PASSWORD=mysecretpassword wetransform/geoserver-appschema
```
