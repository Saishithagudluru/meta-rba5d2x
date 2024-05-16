DESCRIPTION = "sample python programs for upm"
LICENSE = "CLOSED"
SRC_URI = "file://*"
S = "${WORKDIR}"
do_install(){
	install -d ${D}/home
	install -d ${D}/home/root
	install -d ${D}/home/root/upm-examples
	install -m 0777 ${S}/hdc1000.py ${D}/home/root/upm-examples
	install -m 0777 ${S}/mag3110.py ${D}/home/root/upm-examples
	install -m 0777 ${S}/mma8x5x.py ${D}/home/root/upm-examples
	install -m 0777 ${S}/mpl3115a2.py ${D}/home/root/upm-examples
	install -m 0777 ${S}/tcs37727.py ${D}/home/root/upm-examples
	install -m 0777 ${S}/tmp006.py ${D}/home/root/upm-examples
}
FILES_${PN} = "/home/root/upm-examples/*"
