> In case of any detailed questions and puzzles, please refer to this structured and encyclopedic [guidance](https://git-scm.com/book/en/v2 "Git Pro Web"), with **patience** and **concentration** :)

#### @ Git 的 3 种工作空间

本地的 Git 始终在三个区域之间工作
- 本地当前版本库（*branch）
- 当前工作区（HEAD）
- 当前暂存区（stage）
> 在 git restore --help 中使用如下称呼，修改历史（source），当前工作区（working tree = HEAD），当前暂存区（index = stage）


#### @ Git 的 4 类文件状态

 - ignored files (不会被 `git diff` 检测, 无法被 `git add` )
	 - | `移出 .gitignore 文件`
	 - v
 - untracked files (不会被 `git diff` 检测)
	 - | `git add -u <file>` 
	 - | 或
	 - |`git add --include-untracked <file>`
	 - v
 - unstaged files (不会被 `git commit` 提交)
	 - | `git add <file>`
	 - v
 - staged files


#### @  git status 命令

   ```shell
   On branch xxx
   
   ( 对比 暂存区stage 和 版本库 )
   Changes to be committed:
   	new file:
   	modified:
   	deleted:
   	
   ( 对比 暂存区stage 和 当前工作区HEAD )
   Changes not staged for commit:
   	new file:
   	modified:
   	deleted:
   ```


#### @  查看 Git 历史日志和可视化

```shell
gitk // 可视化

git log // 详细日志查询

git log --pretty=oneline // 简明（in one line）日志查询

git log --graph // 树状图日志

git reflog // 历史日志查询（用于版本回退后，找回“未来”的commit）
```


#### @  3种常见错误和解决方式

1. 工作区（HEAD）内的文件被乱改，想要复原
	- if (stage != Null) // after git add
		- HEAD = stage
	- if (stage == Null) // without git add
		- HEAD = latest branch

	```shell
	git restore <file>
	或
	git checkout -- <file>
	```

2. 暂存区（stage）内 `git add` 了错误的文件，想要从 stage 中移除，且不删除（HEAD）中的文件 (解释请参考[后文](#git-reset-default)) 

	  ```shell
	  git restore --staged <file>
	  或
	  git reset HEAD <file>
	```

3. 不仅 `git add` 了错误的文件进入 stage ，而且已经 `git commit` 到本地版本库了，便需要使用版本回退

	```shell
	git restore -s <commit_id><file>
	或
	git reset --hard <commit_id> 
	```

#### @ github 和 Git

- 当涉及到多人协作时，为了避免占用资源，可以选择 github 作为云端 master（多机通讯中的主机，而非本地 branch 中的主分支）
- 每台本地设备都可作为 servant 和主机通讯
- 这也是为什么明明已经 `commit` 到了本地的 master branch 上面，但为了把远程的主机和本地进度进行同步，还需要再 `push`

#### @ 关于远程服务器 \<remote\>

> Please refer to this [link](https://git-scm.com/book/en/v2/Git-Branching-Remote-Branches, "about remote branch").

- 推送到远程服务器

  ```shell
  git push origin <branch-name> // 将本地的 branch 分支推送到远程仓库上的同名分支
  
  git push origin branch-local:branch-cloud // 将本地的 branch-local 分支推送到远程仓库上的 branch-cloud 分支
  ```

- 跟踪远程服务器
  ```shell
  git branch -u origin/cloud // 使当前所在本地 branch 追踪远程分支 branch
  或
  git branch --set-upstream-to origin/cloud
  ```

- 从远程服务器中拉取并跟踪
  ```shell
  git checkout -b branch-local origin/branch-cloud // 将远程分支 cloud 拉取到本地, 建立名为的 branch-local 本地克隆, 并且跟踪远程 cloud
  
  git checkout --track origin/branch-cloud // 简化上行命令, 默认本地克隆与远程分支名字相同
  
  git checkout <branch-name> // 条件
  // 1. <branch-name> 分支在本地不存在
  // 2. 远程有唯一与 <branch-name> 的同名分支
  ```

- 删除 github 远程服务器中的指定分支

  ```shell
  git push origin --delete <remote-branch-name>
  ```

- 查看本地和远程各个 branch 之间的 tracking 关系 

  ```shell
  git branch -vv
  ```


#### @ 关于 git pull 和 git fetch


- 先说结论

  ```
  git pull == git fetch + git merge
  ```

- origin 和 remote 分别是什么? 存在着3个 repo

  - 本地 `<local>`：当在本地文件夹使用`git init`后，所创建的 repo

  - 远程 `<server>`：在 github上创建的 repo

  - 本地对于远程的镜像 `<remote>`（一般起名为origin）：在 local中使用`git remote add <remote>(origin) <server-link>`后，在本地创建的 repo

- 使用`git fetch`后，是将 `<server>` 同步到 `<remote>` (origin)，经过`git merge`后，才真正把 `<remote>` (origin) 和 `<local>` 合并

#### @ git conflict 的情况
> Please refer to this [link](https://vue3js.cn/interview/git/conflict.html#%E4%BA%8C%E3%80%81%E5%88%86%E6%9E%90, "how to deal with git conflict")

#### @ fatal: refusing to merge unrelated histories
- 原因: 在 `git remote add <new_orign> <repo-link>` 之前, repo 之中原本就有旧有残留, 此时 `pull` 或者 `merge` 相当于对两个没有任何公共节点的 `branch` 做合并
- 解决方案
	```shell
	git pull origin master --allow-unrelated-histories
	```

#### @ git rebase 和 git merge
> Please refer to this [link](https://vue3js.cn/interview/git/git%20rebase_%20git%20merge.html#rebase, "git rebase and git merge")

 - 相同点
	- `merge`和`rebasea`都是合并历史记录，都会自动寻找最新的共同 commit 作为合并起点

- 不同点
	- `git merge`合并分支会新增一个`merge commit`，该条 commit 的 parent 来自两个 branch，以将两个分支的历史联系起来
	- `git merge`其实是一种**非破坏性**的操作，对现有分支不会以任何方式被更改，但会导致历史记录愈发复杂
	- `git rebase`会将整个分支嫁接到另一个分支上，线性地仿造中间所有提交，有效地整合了所有分支上的提交
	- `git rebase`会"消除"非必要的分支，使得历史记录**更加清晰**，但旧有分支上的 commit 实际上没有被消除，可以用 `git reflog` 看到并 `reset`
	- 注意 `git rebase` 之后还需要使用 `git merge`

- 使用方法

	- git merge
		```shell
		##### Example for git merge #####
		
		git merge <branch-to-be-merged>
		
		==> mannually fix conflicts
		
		git commit -m "commit msg"
		```
		
	- git rebase
		```shell
		##### Example for git rebase #####
		
		git switch <branch-to-merge>
		git rebase master // 注意这里主从关系和 merge 恰恰相反
		
		==> mannually fix conflicts
		
		git add <conflicted-files>
		git rebase --continue // 注意不再使用 git commit
		或
		git rebase --abort // 放弃 rebase,回到当前 branch
		或
		git rebase --skip // 这里直接跳过 conflicted-commit, 有丢失重要 commit 的风险, 尽量不要用!
		
		git switch master // 注意此时 HEAD -> <branch-to-merge> 而非 master
		
		git merge <branch-to-be-merged> // 此步骤必不可少
		```

#### @ git stash

> Please refer to this [link](https://vue3js.cn/interview/git/git%20stash.html "about git stash")

`git stash` 保存当前工作进度在一个栈 stack 上，后续你可以在任何时候/任何分支重新将某次的记录提取

默认情况下，`git stash`会缓存下列状态的文件

-   添加到暂存区的修改（staged changes）
-   Git跟踪的但并未添加到暂存区的修改（unstaged changes）

但以下状态的文件不会缓存

-   在工作目录中新的文件（untracked files）
-   被忽略的文件（ignored files）

如果想要上述的文件都被缓存，可以使用`-u`或`--include-untracked`可以跟踪工作目录新的文件，使用`-a`或者`--all`命令可以当前目录下的所有修改

#### @ git reset 和 git revert

##### git reset

- `reset`用于回退版本，可以遗弃不再使用的提交. 将当前 HEAD 指向过去历史记录中的某一条提交 HEAD -> <commit_id>

- 默认情况下, `git reset` 只会同步工作空间中的暂存区 (stage), 可以指定参数来修改 

	-   `--mixed`（default）：只有暂存区变化
	    
	-   `--hard`：工作区也会变化 ()
	    
	-   `--soft`：暂存区和工作区都不会变化
	
	<a name="git-reset-default"></a>
	
	> 不指定 <commit_id> 时, 默认为 HEAD, 因此常用 `git reset` 命令来复原错误编辑的文件到原状

##### git revert

-   `reset`是把 HEAD 向后移动了一下，而`revert`是新增一次提交, HEAD 继续前进，只是新的 commit的内容和要 revert 的内容正好相反，能够抵消要被 revert 的内容
	
	```shell
	git revert <commit_id> // 抵消某一个版本
	
	git revert HEAD // 抵消上一次提交
	```
	
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTAyNzkzNDY0NCwtMjEzNzUyMzQzLDEzMD
E0MTAyNTcsLTMwNzI1MjkwOSwtMTIyNTU1ODgwMiwtMTg0NTg0
NTUyOSwtMTMxMjc3OTAxMSwtMTgwNDg3NDA0MywtMTI1MDI2NT
U5MiwtMjQ4NzA2NDE1LDUzNTU4MTg0MiwtMTM0NTQ0MTE3Niwx
MjkyMTQxMTI5LC00NzMwNjMwODksOTg3NDc0ODE0LC0xMTM1MD
gzNzE3LC0xNjM4Nzg5NDUyLDIwMzE0OTE2NTMsMTkwMDA5OTA0
NCwtMzgyNTA0NjE1XX0=
-->