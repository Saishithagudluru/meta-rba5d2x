SUMMARY = "Linux Library for low speed I/O Communication"
HOMEPAGE = "https://github.com/intel-iot-devkit/mraa"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b92a3b497d7943042a6db40c088c3f2"

SRC_URI = "git://github.com/intel-iot-devkit/mraa.git"
SRC_URI += "file://0001-added-support-for-AM5D27-Rugged-board.patch \
          file://0002-pwm_support_sama5d2.patch \
          file://0001-added-support-for-adc-in-mikrobus.patch \
          file://0003-added-support-for-uart3-and-gpios.patch \
          file://0001-FindNodejs.cmake-parse-V8_MAJOR_VERSION-from-nodejs-.patch"
SRCREV = "967585c9ea0e1a8818d2172d2395d8502f6180a2"

S = "${WORKDIR}/git"

# CMakeLists.txt checks the architecture, only x86 and ARM supported for now
COMPATIBLE_HOST = "(x86_64.*|i.86.*|aarch64.*|arm.*)-linux"

inherit cmake distutils3-base

DEPENDS += "json-c "

EXTRA_OECMAKE_append = " -DINSTALLTOOLS:BOOL=ON -DFIRMATA=ON -DCMAKE_SKIP_RPATH=ON "

FILES_${PN}-doc += "${datadir}/mraa/examples/"

FILES_${PN}-utils = "${bindir}/"

# override this in local.conf to get needed bindings.
#BINDINGS_pn-mraad="python"
# will result in only the python bindings being built/packaged.
BINDINGS ??= "python "

PACKAGECONFIG ??= "${@bb.utils.contains('PACKAGES', 'node-${PN}', 'nodejs', '', d)} \
 ${@bb.utils.contains('PACKAGES', '${PYTHON_PN}-${PN}', 'python', '', d)}"

PACKAGECONFIG[python] = "-DBUILDSWIGPYTHON=ON, -DBUILDSWIGPYTHON=OFF, swig-native ${PYTHON_PN},"
PACKAGECONFIG[nodejs] = "-DBUILDSWIGNODE=ON, -DBUILDSWIGNODE=OFF, swig-native nodejs-native,"
PACKAGECONFIG[ft4222] = "-DUSBPLAT=ON -DFTDI4222=ON, -DUSBPLAT=OFF -DFTDI4222=OFF,, libft4222"

FILES_${PYTHON_PN}-${PN} = "${PYTHON_SITEPACKAGES_DIR}/"
RDEPENDS_${PYTHON_PN}-${PN} += "${PYTHON_PN}"

FILES_node-${PN} = "${prefix}/lib/node_modules/"
RDEPENDS_node-${PN} += "nodejs"

### Include desired language bindings ###
PACKAGES =+ "${@bb.utils.contains('BINDINGS', 'nodejs', 'node-${PN}', '', d)}"
PACKAGES =+ "${@bb.utils.contains('BINDINGS', 'python', '${PYTHON_PN}-${PN}', '', d)}"
do_compile(){
	cd ${S}/
	mkdir build
	cd build
	cmake ..
	make
}

do_install_append(){
	install -d ${D}/data/lib
	install -d ${D}/usr/lib
	install -m 0777 ${S}/build/src/libmraa.so.2.0.0 ${D}/data/lib/libmraa.so.2.0.0
	cd ${D}/usr/lib
	rm -rf libmraa.so.2
	rm -rf libmraa.so
	ln -sf /data/lib/libmraa.so.2.0.0 ${D}/usr/lib/libmraa.so.2
	ln -sf libmraa.so.2.0.0 libmraa.so
	install -d ${S}/../../../upm/1.7.0-r0/recipe-sysroot/usr/lib/
}

INSANE_SKIP_${PN} = " \
    installed-vs-shipped \
    ldflags \
"
FILES_${PN} = "/data/lib/libmraa.so.2.0.0 /usr/lib/libmraa.so.2"
