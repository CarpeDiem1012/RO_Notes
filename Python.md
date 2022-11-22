## @ Everything about <u>underline</u>

- `_entity` 是伪 private 变量/方法，一种约定
	- [x] 直接通过 `instance._entity` 访问
	- [x] 被子类继承
	- [ ] 被 `*` import -> 直接 import 名字
- `entity_` 是区别系统关键字 ，一种约定
	- [x] 相当于普通变量/方法
- `__entity` 是真 private 变量/方法
	- [ ] 直接通过 `instance.__entity` 访问 -> `instance._Classname__entity`
	- [ ] 被子类以 `self.__entity` 继承 -> `self._Classname__entity`
- `__entity__` 是 Python 自带神奇变量/方法
	- [x] 直接通过 `Class.__entity__` 访问
	- [x] 被子类继承
	- [x] 直接被 import
	- [ ] 自定义 

- `__name__` 是当前模块名，当模块被直接运行时, 有`__name__ == '__main__'`,代码块将被运行;当模块被导入时，`__name__ != '__main__'`,代码块不被运行。

	> 每个Python模块（Python文件）都有内置变量`__name__`，当模块 my_module.py 被执行的时候，`__name__`等于 'my_module.py' （包含后缀.py）。如果import到其他模块中，则`__name__`等于 'my_module'（不包含后缀.py）。而'\_\_main__'等于当前执行文件的名称（包含后缀.py）。

---
## @ pip install -e/--editable .
此命令关联一个特殊文件叫做 `setup.py` ，本质是在当前文件夹下寻找并且执行文件 `setup.py` ，并且以 develop mode 启动项目。在此模式下，对于当前 venv 下安装的全部 module 都可以直接通过编辑来修改源代码（包括 numpy 之类的标准库），同 venv 下的其他 proj 会共享这个修改版的 module。更适合自由开发但同时也更危险。

## @ Import self-defined  module

假设现在有如下的文件结构
```
|__ A.py
|__ my_module
	|__ B.py
```

- 在 A 中导入 B 的做法:
	```python
	import my_module.B
	B()
	```
	
	但在此之前, 需要在 my_module 下添加一个名为 \_\_init__.py 的空文件, 使得 `my_module` 成为一个 `package`
	```
	|__ A.py
	|__ my_module
		|__ __init__.py
		|__ B.py
	```
	
- 在 B 中导入 A 的做法:
	```python
	import os
	import sys
	sys.path.append('..')
	
	import A
	A()
	```

- 关于 `__init__.py`
类似 ROS 中的 `manifest.xml` 文件，包含 `__init__.py` 文件的会被 Python 识别为一个模组 `Module` 。
如果模组定义文件 `__init__.py`  存在一个叫做  `__all__`  的列表变量，那么在使用  `from my_module import *`  的时候就把这个列表中的所有名字作为包内容导入，例如：
	```python
	__all__ =  ["echo",  "surround",  "reverse"]
	```
	作为包的作者，可别忘了在更新包之后保证  `__all__`  也更新了啊

## @ python 格式化输入输出
``` python
x = 'name: %s; score: %d' % (name, n)
x = 'name: {}; score: {}'.format(name, n)
x = f'name: {name}; score: {n}'
```

---
## @ Script header

- Linux 中写 Python script 和 Shell script 之前, 最好在文件开头指明 interpreter. 
	
	```python3
	#!/usr/bin/python3
	```
	
	```shell
	#!/usr/bin/bash
	```
	
	此时, 可以直接在当前路径下使用`sudo chmod +x` 后直接`./my_script`来执行文件
	假如不用`#!`指明, 则不能直接执行, 而需要在脚本名前添加对应的 interpreter 来协助, 例如`python3 ./my_script.py`

- 该手法可以用于指定 virtue envs 中的 interpreter, 注意global variable `~` 不能出现在路径中, 只能使用解析后的绝对路径, 例如:

	```shell
	#!/home/liangchen/anaconda3/envs/bin/python3.7 -> Yes
	#!~/anaconda3/envs/bin/python3.7 -> No
	```

---
## @ Python install requirement.txt
Please refer to [here](https://note.nkmk.me/en/python-pip-install-requirements/, "about how to make requirements.txt file") on simple syntax for writing your own requirements.txt file.

---
## @ Switch Python3 version

``` shell
$ update-alternatives --config python
> There are 2 choices for the alternative python3 (providing /usr/bin/python3).

  Selection    Path                Priority   Status
------------------------------------------------------------
* 0            /usr/bin/python3.7   2         auto mode
  1            /usr/bin/python3.6   1         manual mode
  2            /usr/bin/python3.7   2         manual mode

Press <enter> to keep the current choice[*], or type selection number: 
```

---
## @ python<xxx> -m pip install

为了避免 pip3 和 pip 只能针对当前 python3 和 python 指向的 interpreter, 可以使用如下命令指定 python 版本
```shell
python<x.x> -m pip <cmd> <option>
```
可以轻松为 python3.6 和 python3.7 安装不同的 pkg

---
## @ with...as... 的本质

1. 本质是一种上下文管理器，其修饰的类型必须包含两种 magic method `__enter__()` 和 `__exit__()`
2. 先执行 `__enter()__`，拿到其返回值作为 `as` 的值
3. 再执行 `with` 后的操作
4. 最后执行 `__exit__()`
5. `__exit__()` 可以捕捉异常信息，根据错误类型进行异常处理

```python
class classAfterWith():
	def __enter__(self):
		print(f"Enter class withas...")
		return self
	def __exit(self, value, type, trace):
		print(f"value={value}")
		print(f"type={type}")
		print(f"trace={trace}")
		return True
```


---
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0NzAxNzU2NDAsLTUzMTg5ODQ3LDE5Nj
YxMTk4MzAsLTE2MzY4NDk3OTgsLTE0NzAyOTkzNTUsLTEyMTI1
NTMyMjEsLTExNzY0MDI1MywxMTg4MTkwNTk4LDE3Mjg0ODQ2Mj
AsLTExMTY1MzU0NjksLTE3ODM4NDg2MDcsMzk3MzUyMjg1LC0x
NDEzOTAyMTM0LC0xOTQ0NTM4OTMsLTIwMzUzNTczMzcsLTEyOD
E3NTMwNzMsMTY4MjE4OTM4LC0xNzA0NjM5Mzg0LC0xOTg1Mjk5
MjgxLDgwNTgyMzY2M119
-->