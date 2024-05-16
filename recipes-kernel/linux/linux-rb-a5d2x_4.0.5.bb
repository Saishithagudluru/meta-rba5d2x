SECTION = "kernel"
DESCRIPTION = "Linux kernel for Atmel ARM SoCs (aka AT91)"
SUMMARY = "Linux kernel for Atmel ARM SoCs (aka AT91)"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

S = "${WORKDIR}/git"

SRCREV = "9de65a00acf411a1a451b1752708a9804f715def"

KBRANCH = "linux-rb-a5d2x_v4.0.5"
SRC_URI = "git://github.com/mohseen-ahamad/linux-rb-a5d2x.git;protocol=git;branch=${KBRANCH}"
SRC_URI += "file://defconfig"
#SRC_URI[md5sum] = "c420b5fdadece5f49d37e91646dc7617"

python __anonymous () {
        if d.getVar('UBOOT_FIT_IMAGE', True) == 'xyes':
                d.appendVar('DEPENDS', ' u-boot-mkimage-native dtc-native')
}

do_deploy_append() {
        if [ "${UBOOT_FIT_IMAGE}" = "xyes" ]; then
                DTB_PATH="${B}/arch/${ARCH}/boot/dts/"
                if [ ! -e "${DTB_PATH}" ]; then
                        DTB_PATH="${B}/arch/${ARCH}/boot/"
                fi

                if [ -e ${S}/arch/${ARCH}/boot/dts/${MACHINE}.its ]; then
                        cp ${S}/arch/${ARCH}/boot/dts/${MACHINE}*.its ${DTB_PATH}
                        cd ${DTB_PATH}
                        mkimage -f ${MACHINE}.its ${MACHINE}.itb
                        install -m 0644 ${MACHINE}.itb ${DEPLOYDIR}/${MACHINE}.itb
                        cd -
                fi
        fi
        rm -rf ${S}/../image/boot/zImage
}

kernel_do_configure_append() {
        rm -f ${B}/.scmversion ${S}/.scmversion
        cd ${S}; git status; cd -
        rm -rf ${S}/../image/boot/zImage
}

do_shared_workdir () {
        rm -rf ${S}/../image/boot/zImage
        rm -rf ${S}/../deploy-linux-rba5d2x/zImage
}

addtask shared_workdir after do_deploy_append

KERNEL_MODULE_AUTOLOAD += "atmel_usba_udc g_serial"
KERNEL_MODULE_PACKAGE_SUFFIX="rugged_board"

COMPATIBLE_MACHINE = "(sama5d2-xplained|sama5d2-xplained-sd|sama5d2-ptc-ek|sama5d2-ptc-ek-sd|sama5d27-som1-ek|rugged-board-a5d2x|rugged-board-a5d2x-sd1|sama5d27-som1-ek-sd|sama5d4-xplained|sama5d4-xplained-sd|sama5d4ek|sama5d3-xplained|sama5d3-xplained-sd|sama5d3xek|at91sam9x5ek|at91sam9m10g45ek|at91sam9rlek)"
