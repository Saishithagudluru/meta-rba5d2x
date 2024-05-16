SUMMARY = "Sensor/Actuator repository for Mraa"
HOMEPAGE = "https://github.com/intel-iot-devkit/upm"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=66493d54e65bfc12c7983ff2e884f37f"

DEPENDS = "libjpeg-turbo mraa-data"

SRC_URI = "git://github.com/intel-iot-devkit/upm.git \
           file://0004-remove-examples-from-cmakefile.patch \
           file://0007-starting-with-a-files.patch \
           file://0008-starting-with-b-files.patch \
           file://0009-starting-with-cde-files.patch \
           file://0010-starting-with-fghi-files.patch \
           file://0011-starting-with-jklm-files.patch \
           file://0012-starting-with-noprs-files.patch \
           file://0015-starting-with-t-files.patch \
           file://0016-starting-with-uv-files.patch \
           file://0001-starting-with-w-files.patch \
           file://0001-starting-with-xy-files.patch \
           file://0001-added-support-for-examples-in-library.patch \
"
SRCREV = "67b77b78aa21dbc249acbc6693972f638bbfc962"

#SRC_URI[md5sum] = "c60b87b9ff2ee4135e16f62e099487e8"
#SRC_URI[sha256sum] = "66ac1a869b0b06144a3ebe96c20b2e4195ec9c1879d14bde77ac706d237dfca8"

S = "${WORKDIR}/git"

# Depends on mraa which only supports x86 and ARM for now
COMPATIBLE_HOST = "(x86_64.*|i.86.*|aarch64.*|arm.*)-linux"

inherit distutils3-base cmake
EXTRA_OECMAKE += "-DBUILDEXAMPLES=ON "
# override this in local.conf to get needed bindings.
# BINDINGS_pn-upm="python"
# will result in only the python bindings being built/packaged.
BINDINGS ??= "python "
#BINDINGS_python-upm="python"

# nodejs isn't available for armv4/armv5 architectures
BINDINGS_armv4 ??= "python"
BINDINGS_armv5 ??= "python"

PACKAGECONFIG ??= "${@bb.utils.contains('PACKAGES', 'node-${PN}', 'nodejs', '', d)} \
 ${@bb.utils.contains('PACKAGES', '${PYTHON_PN}-${PN}', 'python', '', d)}"

PACKAGECONFIG[python] = "-DBUILDSWIGPYTHON=ON, -DBUILDSWIGPYTHON=OFF, swig-native ${PYTHON_PN},"
PACKAGECONFIG[nodejs] = "-DBUILDSWIGNODE=ON, -DBUILDSWIGNODE=OFF, swig-native nodejs-native,"

FILES_${PYTHON_PN}-${PN} = "${PYTHON_SITEPACKAGES_DIR}"
RDEPENDS_${PYTHON_PN}-${PN} += "${PYTHON_PN}"

FILES_node-${PN} = "${prefix}/lib/node_modules/"
RDEPENDS_node-${PN} += "nodejs"

### Include desired language bindings ###
PACKAGES =+ "${@bb.utils.contains('BINDINGS', 'nodejs', 'node-${PN}', '', d)}"
PACKAGES =+ "${@bb.utils.contains('BINDINGS', 'python', '${PYTHON_PN}-${PN}', '', d)}"

do_install_append(){
	install -d ${D}/data/lib
	install -d ${D}/usr/lib
	cd ${D}/usr/lib/
	cp -r *.so.1.7.0 ${D}/data/lib/
	rm -rf *.so.1
	rm -rf *.so.1.7.0

	ln -s /data/lib/libupm-bmp280.so.1.7.0 libupm-bmp280.so.1
	ln -s /data/lib/libupmc-bmp280.so.1.7.0 libupmc-bmp280.so.1
	ln -s /data/lib/libupmc-lsm6dsl.so.1.7.0 libupmc-lsm6dsl.so.1
	ln -s /data/lib/libupmc-utilities.so.1.7.0 libupmc-utilities.so.1
	ln -s /data/lib/libupm-interfaces.so.1.7.0 libupm-interfaces.so.1
	ln -s /data/lib/libupm-lsm6dsl.so.1.7.0 libupm-lsm6dsl.so.1
	ln -s /data/lib/libupm-mcp9808.so.1.7.0 libupm-mcp9808.so.1
	ln -s /data/lib/libupm-utilities.so.1.7.0 libupm-utilities.so.1
	ln -s /data/lib/libupm-zfm20.so.1.7.0 libupm-zfm20.so.1
}

INSANE_SKIP_${PN} = " \
    installed-vs-shipped \
    ldflags \
"
FILES_${PN} = "/data/lib/*.so.1.7.0 /usr/lib/*.so.1"
