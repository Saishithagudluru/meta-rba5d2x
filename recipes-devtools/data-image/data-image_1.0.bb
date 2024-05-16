DESCRIPTION = "hello example"
LICENSE = "CLOSED"
SRC_URI = "file://data_lib.sh \
	   file://interfaces \
	   file://env.sh \
"

S = "${WORKDIR}"

DEPENDS += "libmodbus-data lighttpd-data mraa-data upm-data"

do_install(){
	install -d ${D}/data
	install -d ${D}/etc/init.d
	install -d ${D}/usr/lib
	install -d ${S}/../../../../deploy/images/rugged-board-a5d2x
	install -d ${WORKDIR}/data-image/lib ${WORKDIR}/data-image/www/var ${WORKDIR}/data-image/network
	cp -r ${S}/data_lib.sh ${WORKDIR}/data-image/
	cp -r ${S}/../../mraa-data/*/image/data/lib/libmraa.so.2.0.0 ${WORKDIR}/data-image/lib/
	cp -r ${S}/../../mraa-data/*/git/build/examples/c/hellomraa ${WORKDIR}/data-image/

	# for libmodbus
	cp ${S}/../../libmodbus-data/*/libmodbus-data-3.1.4/src/.libs/libmodbus.so.5.1.0 ${WORKDIR}/data-image/lib/

	# for lighttpd
	cp -r ${S}/../../lighttpd-data/*/build/src/.libs/mod_access.so ${WORKDIR}/data-image/lib/
	cp -r ${S}/../../lighttpd-data/*/build/src/.libs/mod_accesslog.so ${WORKDIR}/data-image/lib/
	cp -r ${S}/../../lighttpd-data/*/build/src/.libs/mod_dirlisting.so ${WORKDIR}/data-image/lib/
	cp -r ${S}/../../lighttpd-data/*/build/src/.libs/mod_indexfile.so ${WORKDIR}/data-image/lib/
	cp -r ${S}/../../lighttpd-data/*/build/src/.libs/mod_staticfile.so ${WORKDIR}/data-image/lib/

	#for network interface
	cp -r ${S}/interfaces ${WORKDIR}/data-image/network/

	#for startup application env file
	cp -r ${S}/env.sh ${WORKDIR}/data-image/env.sh

	/usr/sbin/mkfs.jffs2 -lnp --root=${WORKDIR}/data-image --eraseblock=0x10000 -p -o ${S}/../../../../deploy/images/rugged-board-a5d2x/data-image-rootfs.jffs2
}

INSANE_SKIP_${PN} = " \
    ldflags		\
    installed-vs-shipped \
"
FILES_${PN} += "/usr/lib/libmraa.so "
