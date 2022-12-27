# Creates the "feed", packages not required for the image
# but that should be built for the feed so that other
# components may use them and install on demand.

# We have a GPLv2 license for this recipe...
require conf/license/license-gplv2.inc

# Depend on the image, so that it gets build
DEPENDS = "areadeltasat-enigma2-image"


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

OPTIONAL_PACKAGES ?= ""
OPTIONAL_BSP_PACKAGES ?= ""
OPTIONAL_BSP_ENIGMA2_PACKAGES ?= ""

# Get the kernel version for this image, we need it to build conditionally on kernel version
# NB: this only works in the feed, as the kernel needs to be build before the headers are available
export KERNEL_VERSION = "${@oe.utils.read_file('${PKGDATA_DIR}/kernel-depmod/kernel-abiversion')}"

# Out-of-tree wifi drivers, build conditionally based on kernel version
OPTIONAL_WIFI_PACKAGES = "\
	${@ 'kernel-module-8812au' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.0') < 0) else '' } \
	${@ 'kernel-module-8814au' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.0') < 0) else '' } \
	${@ 'kernel-module-rt5572sta' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.10') < 0) else '' } \
	${@ 'kernel-module-rt8188eu' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.12') < 0) else '' } \
	${@ 'kernel-module-rt3573sta' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.12') < 0) else '' } \
	${@ 'kernel-module-mt7601usta' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.2') < 0) else '' } \
	${@ 'kernel-module-8723a' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.5') < 0) else '' } \
	${@ 'kernel-module-8723bu' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.6') < 0) else '' } \
	${@ 'kernel-module-8192eu' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.7') < 0) else '' } \
	${@ 'kernel-module-mt7610u' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.19') < 0) else '' } \
	\
	${@ 'kernel-module-8192fu' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.8') >= 0) else '' } \
	${@ 'kernel-module-8821cu' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.8') >= 0) else '' } \
	${@ 'kernel-module-88xxau' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.0') >= 0) else '' } \
	\
	${@bb.utils.contains('MACHINE_ESSENTIAL_EXTRA_RDEPENDS', 'rtl8723bs', '', bb.utils.contains('MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS', 'spycat-rtl8723bs', '', 'kernel-module-r8723bs' if ("${KERNEL_VERSION}" and "${MACHINE}" != "dm8000" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.12') < 0) else '', d), d)} \
	\
	kernel-module-88x2bu \
	kernel-module-8189es \
	firmware-rtl8723bu \
	firmware-rtl8188eu  \
	firmware-rtl8192eu \
	firmware-mt7601u \
	"
#	** TODO **
#	rtl8723au_bt
#	rtl8723au

OPTIONAL_PACKAGES += " \
	astra-sm \
	autofs \
	autossh \
	ccid \
	cronie \
	ctorrent \
	cups \
	davfs2 \
	diffutils \
	djmount \
	dosfstools \
	dvb-apps \
	dvblast \
	dvbsnoop \
	edid-decode \
	evtest \
	exfat-utils \
	exteplayer3 \
	grep \
	gstplayer \
	hddtemp \
	hdparm \
	htop \
	inadyn-mt \
	inetutils \
	inotify-tools \
	iperf3 \
	iproute2 \
	iputils \
	joe \
	less \
	libbluray \
	libsdl2 \
	libudfread \
	mc \
	mediainfo \
	pv \
	minisatip \
	mtd-utils \
	mtools \
	nano \
	ndisc6 \
	net-snmp \
	net-tools \
	${@bb.utils.contains('TARGET_FPU', 'soft', '', '', d)} \
	perl \
	phodav \
	ntfs-3g \
	ntp \
	ofgwrite \
	openresolv \
	openssh \
	openvpn \
	p7zip \
	parted \
	procps \
	pyload \
	${PYTHON_PN}-ipaddress \
	${PYTHON_PN}-ntplib \
	${PYTHON_PN}-pip \
	${PYTHON_PN}-requests \
	${PYTHON_PN}-mechanize \
	${PYTHON_PN}-lxml \
	${PYTHON_PN}-js2py \
	${PYTHON_PN}-pyexecjs \
	${PYTHON_PN}-beautifulsoup4 \
	${PYTHON_PN}-compat2 \
	${PYTHON_PN}-future \
	${PYTHON_PN}-futures \
	${PYTHON_PN}-pycryptodome \
	${PYTHON_PN}-singledispatch \
	${PYTHON_PN}-websocket-client \
	${PYTHON_PN}-isodate \
	${PYTHON_PN}-pycountry \
	${PYTHON_PN}-youtube-dl \
	${PYTHON_PN}-pycryptodomex \
	${PYTHON_PN}-evdev \
	picocom \
	ppp \
	rclone \
	rsync \
	rtorrent \
	sabnzbd \
	satipclient \
	screen \
	sed \
	sshpass \
	sshfs-fuse \
	smartmontools \
	strace \
	streamlink \
	streamlinksrv \
	tcpdump \
	tmux \
	transmission \
	unzip \
	usb-modeswitch \
	usb-modeswitch-data \
	v4l-utils \
	vim \
	wget \
	wscan \
	yafc \
	zeroconf \
	zip \
	zsh \
	${OPTIONAL_BSP_PACKAGES} \
	${OPTIONAL_WIFI_PACKAGES} \
	"

OPTIONAL_ENIGMA2_PACKAGES = " \
	channelsettings-enigma2-meta \
    vhannibal-settings-enigma2-meta  \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-automatic-fullbackup \
	enigma2-plugin-extensions-backupsuite \
	enigma2-plugin-extensions-blurayplayer \
	enigma2-plugin-extensions-customsubservices \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-dlnaserver \
	enigma2-plugin-extensions-epgimport \
	enigma2-plugin-extensions-fontinfo \
	enigma2-plugin-extensions-hdmitest \
	enigma2-plugin-extensions-historyzapselector \
    enigma2-plugin-extensions-infobarweather \
    enigma2-plugin-extensions-weathermsn \
	enigma2-plugin-extensions-jedimakerxtream \
	enigma2-plugin-extensions-managerautofs \
	enigma2-plugin-extensions-modifyplifullhd \
	enigma2-plugin-extensions-moviemanager \
	enigma2-plugin-extensions-openmultiboot \
	enigma2-plugin-extensions-refreshbouquet \
	enigma2-plugin-extensions-sdgradio \
	enigma2-plugin-extensions-subssupport \
	enigma2-plugin-extensions-sundtekcontrolcenter \
	enigma2-plugin-extensions-tmbd \
	enigma2-plugin-extensions-ts-sateditor \
	enigma2-plugin-extensions-vcs \
	enigma2-plugin-extensions-xmodem \
	enigma2-plugin-extensions-xstreamity \
	enigma2-plugin-extensions-youtube \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-systemplugins-devicemanager \
	enigma2-plugin-systemplugins-extnumberzap \
	enigma2-plugin-systemplugins-extrafancontrol \
	enigma2-plugin-systemplugins-hrtunerproxy \
	enigma2-plugin-systemplugins-misplslcnscan \
	enigma2-plugin-systemplugins-mountmanager \
	enigma2-plugin-systemplugins-radiotimesxmltvemulator \
	enigma2-plugin-systemplugins-serviceapp \
	enigma2-plugin-systemplugins-signalfinder \
	enigma2-plugin-systemplugins-terrestrialscan \
	enigma2-plugin-systemplugins-quadpip \
	enigma2-plugin-security-firewall \
    enigma2-plugin-skins-magic \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugin-skins-pli-hd-fullnight \
	enigma2-plugin-skins-simple-gray \
	enigma2-plugin-skins-pd1loi-hd-night \
	enigma2-plugin-skins-glamouraurafhd \
    enigma2-plugin-skins-delta-black-fhd \
    enigma2-plugin-skins-delta-blackblue-fhd \
    enigma2-plugin-skins-delta-blackred-fhd \
    enigma2-plugin-skins-areadeltasat-fhd \
    enigma2-plugin-skins-areadeltasat-fhd-pro \
    enigma2-plugin-skins-revolution-pro \
    enigma2-plugins \
	softcams-enigma2-meta \
    enigma2-plugin-softcams-os-cam \
    enigma2-plugin-softcams-oscam-icam \
	packagegroup-openplugins \
	${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-satscan" , "", d)} \
	${@bb.utils.contains('AREADELTASAT_FEATURES', 'kodi', 'enigma2-plugin-extensions-kodi kodi-addons-meta', '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'kodi', 'enigma2-plugin-extensions-kodi kodi-addons-meta', '', d)} \
	${@bb.utils.contains('AREADELTASAT_FEATURES', 'qtplugins', 'enigma2-plugin-extensions-qthbbtv enigma2-plugin-extensions-qtstalker', '', d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "transcoding", "streamproxy", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
	${@bb.utils.contains('AREADELTASAT_FEATURES', 'openhbbtvbrowser', 'enigma2-plugin-extensions-openhbbtvbrowser', '', d)} \
	dvb-usb-drivers-meta \
	cdtextinfo \
	${OPTIONAL_BSP_ENIGMA2_PACKAGES} \
	"

DEPENDS += "${OPTIONAL_PACKAGES} ${OPTIONAL_ENIGMA2_PACKAGES}"