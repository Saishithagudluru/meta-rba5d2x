require at91bootstrap.inc

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

BBRANCH = "rb-at91bootstrap_v3.8.12"

SRC_URI += "git://github.com/mohseen-ahamad/at91-bootstrap_v3.8.12.git;protocol=https;branch=${BBRANCH}"

PV = "3.8.12+git${SRCPV}"

SRCREV = "9aa360bf0bf390bbe0a182fdb48fe7a7098d6bf7"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = '(sama5d3xek|sama5d3-xplained|sama5d3-xplained-sd|at91sam9x5ek|at91sam9rlek|at91sam9m10g45ek|sama5d4ek|sama5d4-xplained|sama5d4-xplained-sd|sama5d2-xplained|sama5d2-xplained-sd|sama5d2-xplained-emmc|sama5d2-ptc-ek|sama5d2-ptc-ek-sd|sama5d27-som1-ek|sama5d27-som1-ek-sd|rugged-board-a5d2x)'
