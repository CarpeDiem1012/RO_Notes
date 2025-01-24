# 

## Python in Ubuntu
- 首先，linux 本身的运行是部分基于 python 的，因此任何 Ubuntu 自安装起都会自带一个系统级别的 python interpreter for system，通常位于 `/usr/bin/python` 可以通过以下命令查看
  
  ``` shell
  $ where python
  ```

- 永远不要冒险手动 `ln -s` 为这个系统自带的创建 symlink，这个interpreter 会涉及到 apt, Xorg, gi 等系统级别的应用，一旦手动更改了指向，看似当前没有影响，但重启或开启新的 terminal 后严重可至系统瘫痪，更不要删除！

- 涉及到 python version management 的需求，标准的解决方案是使用虚拟环境，也就是这篇笔记的主要目的

## Python in Virtual Environment (e.g. Anaconda, venv)

## Python in ROS/ROS2/IsaacSim
> 关键背景：假如想使用 pre-packaged binaries，当前所使用的 python interpreter 必须和提供 pre-packaged binaries 的人所用来 build 的版本一致。如果不一致，则无法使用。很遗憾，IssacSim 虽然支持在其自带的 python terminal 中原生调用 ROS2，但其对应的 pre-packaged binaries 是用 python3.10 编译好的，因此无法使用其他版本的 python 来运行。

- 以上3者均会在安装完成后自带一个 Python interpreter，分别位于各自的 `./bin/` 下
- 使用这些应用之前，无一例外都会需要一部关键的前置操作 `source xxx-setup.bash`，无论你是否以将其加入 `.bashrc` 的方式来默认开启， 这个 `setup.sh` 总是必要的
- 本质上，这个命令最关键的作用之一就是在将其专有的 Python interpreter 的路径 `./xxx/bin/` 加入到系统寻址路径 `$PATH` 之前，这样在不覆盖系统路径的前提下，应用地址会拥有更优先的被索引权限，也就起到了改变当前 Python interpreter 的作用（相当于把系统截胡了）

## Ubuntu 自带的 Python3.8 在 以 3.10 为基础编译的IsaacSim 和 ROS2 Humble 中可以使用吗？
- IsaacSim 和 ROS2 Humble 在其 bin/ 下自带的都是 Python3.10，对应的 pre-packaged binaries 也只能兼容3.10。但是 ROS2 可以用 3.8 或者 3.10 build from source


## 小结

- pip 是 Python 自己的 package 管理工具，使用该命令时取决于当前是哪一个 python，有多少个 interpreter 就有多少个 pip 库

- conda 是系统 python之外的第三方环境管理工具，每一个 conda env 可以安装各自的 python x.x，相互之间以及和系统之前不干扰，启动时的 activate 就是在修改 PATH
 
- IsaacSim/ROS2 可以使用自己的 python3.10 interpreter，但 source setup.bash 之后大概会缺少其他 python interpreter 对应的 package，此时可以用其自己的 pip install 安装缺失的环境依赖

  ```shell
  ./python.sh -m pip install <name_of_package>
  ```

- 尝试删除本地的多余 python 版本，先安装 anaconda，创建 env 后在 conda 中选择对应的 python 来对 ROS2+IsaacSim 进行 build from source。随后每次使用都先进入对应的 conda 环境
  - 为 ROS2 Humble +  创建一个 python=3.10 的 conda 环境，在其中 build from source(验证过了不可以，build 之后 rosrun 还是会报错，和 rclpy 有关，大概可以理解为 ros2 底层需要调用一些 pre-packaged 的二进制 .o文件，这些二进制文件是 ros 编译过程中指定 system interpreter 编译的，因此你也只能在 run 的时候使用和 system interpreter 相同版本的 python)
  - 遵循官方教程 build ROS Humble from source in Ubuntu20
  - 中间会遇到多次问题，耐心解决
    - [conda 安装3个 pkg即可解决](https://github.com/colcon/colcon-ros/issues/118) `conda install -c conda-forge catkin_pkg empy lark`
    - `pip install numpy`
    - `colcon build --symlink-install --packages-ignore qt_gui_cpp rqt_gui_cpp intra_process_demo` 或者使用 `touch COLCON_IGNORE` 放在对应 package_xxx/ 下面，空文件就行
    - 以下的都不管用
    - [神级debug能力](https://blog.csdn.net/qq_38606680/article/details/129118491) `cd ~/anaconda3/envs/ros2/lib` + 备份、删除软链接 + `sudo ln -s /lib/x86_64-linux-gnu/libffi.so.7.1.0 libffi.so.7` + `sudo ldcondif`
    - （看情况）`sudo apt-get remove Shiboken2` 重新 `pip install Shiboken2`
    - 
- [RoboStack](https://robofoundry.medium.com/using-robostack-for-ros2-9bb52ca89c12) 是一个开源项目，我的理解其类似于将 ros/ros2 像 docker container 一样跨平台快速部署到任何机器上，不受限与本地系统级别的 python 版本以及其他环境



Reference:
1. [1.How to Properly Manage Multiple Python Versions](https://www.baeldung.com/linux/python-multiple-versions#:~:textWe%20can%20install%20multiple%20Python,default%20version%20of%20the%20interpreter.)

2. [2.Using Python Packages with ROS 2](https://docs.ros.org/en/humble/How-To-Guides/Using-Python-Packages.html)

3. [3. 有可能可以解决3.10安装ROS2 Humble的问题](https://docs.ros.org/en/humble/How-To-Guides/Installation-Troubleshooting.html#linux)