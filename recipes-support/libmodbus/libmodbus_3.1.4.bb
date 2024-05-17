require libmodbus.inc

# Use the stable branch by default
DEFAULT_PREFERENCE = "-1"

SRC_URI[md5sum] = "b1a8fd3a40d2db4de51fb0cbcb201806"
SRC_URI[sha256sum] = "c8c862b0e9a7ba699a49bc98f62bdffdfafd53a5716c0e162696b4bf108d3637"

addtask do_delete before do_package_write_rpm after do_package

do_delete(){
	cd ${S}/../../../../../deploy/rpm/cortexa5hf_neon/
	touch libmodbus5-3.1.4-r0.cortexa5hf_neon.rpm
	touch libmodbus-dbg-3.1.4-r0.cortexa5hf_neon.rpm
	touch libmodbus-dev-3.1.4-r0.cortexa5hf_neon.rpm
	rm libmodbus*
}
