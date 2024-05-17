DESCRIPTION = "Copying the wifi firmware files and modules"
SECTION = "firmware libs"
LICENSE = "CLOSED"

SRC_URI = "file://*"

S = "${WORKDIR}"

do_install() {
             install -d ${D}${libdir}
             install -d ${D}/lib/firmware
             cp -r ${S}/firmware/* ${D}/lib/firmware/
}

FILES_${PN} += "lib/firmware/*"

INSANE_SKIP_${PN} = "ldflags installed-vs-shipped"
