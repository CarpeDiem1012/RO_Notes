> [2024.03.14] 写在前面：用于记录自己安装三硬盘三系统的经验，涉及了分区、安装、Ubuntu 从电脑到移动硬盘的无损系统迁移、手动编译 linux kernel、升级驱动、ubuntu 常用 debug 方法等。本意是记录，仅供参考，如果刚好帮助到需要的朋友就再好不过了。

# 背景知识篇
- 安全检查
  - 首先确保 Microsoft 账号可用，邮箱可以顺利接受邮件，以防 Bitlocker 偷家
  - 在执行任何下述操作前，先进入 Win11 设置关闭 Bitlocker（非常重要，否则严重情况下后果不堪设想）
- BIOS 引导界面（Thinkbook16+ 2024, Ultra 5 + RTX4050）
  - 开机后 F2 进入 BIOS 设置
    - 禁用 secure boot
    - 进入 UEFI 启动项顺序设置
  - 开机后 F12 直接进入 UEFI 启动项顺序设置
- EFI分区：用于引导系统启动的独立分区，本电脑自带的 win11 系统划分了260MB 
  - BIOS：一种老式电脑的启动引导界面（源代码只能位于在 MBR 类型磁盘中的前部空间）
  - UEFI：新式电脑的启动引导界面（GPT 类型磁盘，不限制源代码位置）
  - Grub(2)：Ubuntu 系统常使用的引导界面
- 多硬盘、多系统情况：
  - EFI 分区处于物理硬盘中，用于存放和管理 OS 的 bootloader
  - 可以划分多个 EFI 分别控制各自的 OS，也可以集中在同一个 EFI 分区下集中控制。例如：/nvme0n1p1/efi 下可以同时存放 Microsoft 和 Ubuntu
    > 具体影响在于更换物理硬盘时，EFI 分区是否会跟随硬盘移动。例如： ssd-a 中的 OS-a 由于其 bootloader 存放在 ssd-b 的 efi 下，因而 ssd-b 断开连接后无法启动 OS-a
  - 启动逻辑：
    - 先扫描全部硬盘/存储器中的所有 EFI 分区
    - 【第一个 branch】依据 F12 中的 UEFI 启动顺序，优先启动第一个 UEFI
    - 【第二个 branch】每一个 UEFI 选项可以对应多个 OS，依据 EFI 中的 bootloader 选择 ubuntu/win 系统
  - 举例说明：
    - 个人配置是笔记本电脑中有两块 ssd(ssd-a 和 ssd-b)，另外还有一个移动硬盘 ssd-c。使用 Win11、Ubuntu20.04、Ubuntu18.04 三个系统
    - ssd-a 中存放：
      - efi 分区：用来管理 Win11 + Ubuntu20.04
      - Win11 系统分区
    - ssd-b 中存放：Ubuntu20.04 系统分区（即根目录 \）
    - 移动硬盘 ssd-c 中存放：
      - efi 分区：单独用于管理移动硬盘启动的 Ubuntu18.04（启动前需要 F2 设置 UEFI 启动项）
      - Ubuntu18.04 系统分区（即根目录 \）
    > 现在安装 Ubuntu 不需要为 /home /usr 做独立分区后挂载了，只需一个足够大的分区，全部作为根目录 /即可

# Win11 篇

## 磁盘管理工具
- 下载 DiskGenius
- Win11 自带的磁盘管理工具
- Admin 进入 cmd，使用 diskpart 命令行工具

## Ubuntu 无损系统迁移
前文提过，目前的 Ubuntu 安装思路很简介，只需要一个 EFI 分区管理 bootloader，以及一个根目录 / 即可。因此想实现 Ubuntu 到移动硬盘的系统迁移也只需针对这两个环节即可：
1. 迁移根目录 /：
   1. 原本想使用 DiskGenius 把旧 dell 电脑硬盘中的 Ubuntu 系统分区直接 clone 到移动硬盘的对应分区（提前格式化为ext4）中，但出现了无法解决的磁盘 error
   2. 间接实现目标，利用 DiskGenius 的镜像制作和还原功能，先把原来电脑硬盘中的 Ubuntu 系统分区制作为镜像文件，再还原到移动硬盘中，尝试成功
2. 迁移 EFI：
   1. 提前在移动硬盘中分区
   2. 使用 clone 功能复制旧 EFI 到硬盘中
   3. 删除移动硬盘中新的 EFI 下的 Microsoft 文件夹
   4. 修改 /boot/grub/grub.cfg
   5. 或 修改 /etc/grub.d/40_custom
   6. 或 使用 linux 工具 grub-customizer

# 启动盘篇
准备一个专门的启动盘用于长期管理、安装系统，建议使用 Ventoy 先格式化 U盘（移动硬盘、电脑硬盘也可），选择 GPT 格式，随后下载 .iso 镜像文件即可使用
- 优点：方便快捷、一劳永逸、剩余空间可以作为存储盘使用，无需反复格式化

# Ubuntu 篇
处于兼容性考虑，同时想在不切换系统的情况下兼容使用 ros1 和 ros2，因此选择了已经停止维护的20.04.6LTS 作为安装对象

## debug 历程
由于 20.04.6 使用的是 linux-5.15 kernel，因此虽然可以顺利启动，但无法使用触摸板、wifi、以及 N卡（Intel AX211 Controller + Nvidia RTX4050）
- 尝试1：升级驱动 backprot-iwlwifi 和 固件 linux-firmware，无效
- 尝试2：使用安装包升级内核，尝试了 5.19、6.1、6.2、6.5、6.6 版本内核，不仅无法解决问题，甚至无法启动，会出现启动界面卡死黑屏的情况（尝试过写入nomodeset，无效果），只有 6.7.9 可以丝滑启动，并且解决了 touchpad + wifi + soundcard 的问题，但是无法安装最重要的 nvidia driver
- 尝试3：上述问题有一定可能是 `linihx-header-<version>-generic.deb` 安装失败的结果，这和 Ubuntu20.04 底层 c 代码包的依赖有关系，具体可以定位到
  - libc6(>=2.38) ，使用 libc6_2.38-1ubuntu6.1_amd64.deb
  - libssl3(>=3.0.0)，使用 libssl3_3.0.2-0ubuntu1.15_amd64.deb
   
  本着单车变摩托的想法，尝试卸载并重新安装这两个包，结果不出意外地把系统搞崩了，于是调整心态重装了系统**（建议后来的朋友们不要轻易尝试）**

- 尝试4：万念俱灰之下开始尝试手动编译内核，这里查阅了诸多教程，出现过一些问题，但最后多亏这位博主的[教程](https://www.learndiary.com/2024/01/compile-kernel-in-ubuntu/)，成功带我顺利编译（耗时较长），也在此感谢所有认真分享原创教程，精心构建开源社区的爱好者们！

## 有关 firmware 和 driver
- firmware 本质上需要更新的文件夹是 `/lib/firmware/`，所有 git repo 中的 install.sh 本质上也是在做这件事，因此找到需要添加的组件，`cp -r` 到该地址即可
- driver 的安装则大致通过两种方式
   1. 如果有 release 版本，可以使用 `dpkg -i <filename>.deb` 来安装 .deb 安装包，但此种方式很可能遇到一些 dependency 问题
   2. 上述方式行不通的时候就可以考虑去 git repo 跟着 READEM.md 进行源码编译，使用 `make -j($nproc)` 或 `cmake`，可以

## Touchpad + wifi adapter
- 下载最新的 linux-firmware-<version>.tar，解压到本地，复制全部文件到 `/lib/firmware/` 即可
- 注意不要碰 `/usr/lib/firmware`，会自动同步

## Nvidia driver
- 添加 nvidia 的 ppa repo

    ``

- 查看可用 driver

    `ubuntu-drivers device`

- 使用自动安装模式下载推荐 driver（这里是550版本）
  
    `ubuntu-drivers autoinstall`

## Soundcard not found
- 使用 dmesg 查看开机时的报错信息，定位到问题在于 sof_firmware

  > sudo dmesg | grep error

- 尾随 error 信息去[官方repo](https://github.com/thesofproject/sof-bin/)下载 [latest release](https://github.com/thesofproject/sof-bin/releases) 并 install.sh
- 其余的 firmware 报错可以去 [linux-firmware官网](https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git) 下载最新的 .tar，解压之后找到 error 中对应的文件，复制到 `/lib/firmware/<vendor>` 路径下，重启系统

## 挽救崩溃边缘的系统
在硬件调试过程中，总会有手滑或者神志不清的时刻，例如我曾经一个不注意删除了系统自带的 python，同时系统会检查相关的依赖文件并且删除了一大批相关的底层包。结果是显而易见的：大量应用无法运行，无法唤起 terminal。想要亡羊补牢安装回系统 python 是收效甚微的，因为大量记不住名字的依赖包也被删除了，此时近乎可以重装系统了。但感谢 linux 系统设计的完备性，存在一招气死回生的操作：查 log，详情可以参考这篇[博客](https://blog.csdn.net/havetobegod/article/details/104125368)

- 进入命令行 或 tty
- 找到 /var/log/apt/history.log 文件，查看卸载的包
- 安装所有卸载过的包

## 关于 X11, optimus, nvidia-prime 的设置（不支持独显直连，让 iGPU 负责屏幕显示，dGPU 负责高性能计算） 
- optimus 是混合输出的技术
- nvidia-prime 可以选择输出模式
- X11 的 conf 文件中，正确配置 screen, device, display 的关系（确认好 gpu 在PCI中的 BusID）
- 关于 X11 的配置文件格式，参考[这里](https://www.x.org/releases/current/doc/man/man5/xorg.conf.5.xhtml)