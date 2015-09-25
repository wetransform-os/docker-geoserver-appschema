#!/bin/sh

# Runtime configuration that should not be stored in the image
echo "Executing prelaunch script..."
/tmp/scripts/prelaunch.groovy "$GEOSERVER_DIR"

# Run tomcat
echo "Starting GeoServer..."
/usr/local/tomcat/bin/catalina.sh run
