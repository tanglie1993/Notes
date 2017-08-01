## git 和 svn 的区别
- Git is a distributed VCS; SVN is a non-distributed VCS.
- Git has a centralized server and repository; SVN does not have a centralized server or repository.
- The content in Git is stored as metadata; SVN stores files of content.
- Git branches are easier to work with than SVN branches.
- Git does not have the global revision number feature like SVN has.
- Git has better content protection than SVN.
- Git was developed for Linux kernel by Linus Torvalds; SVN was developed by CollabNet, Inc.
- Git is distributed under GNU, and its maintenance overseen by Junio Hamano; Apache Subversion, or SVN, is distributed under the open source license.

## 怎样配置用户名和邮箱
$ git config --global user.name "username"  
$ git config --global user.email "email"

## 工作区，暂存区是什么

- 工作区：就是你在电脑里能看到的目录，比如 work文件夹 就是个工作区。
- 版本库：工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。Git的版本库里存了很多东西，其中最重要的就是称为stage（或者叫index）的暂存区，还有Git为我们自动创建的第一个分支master，以及指向master的一个指针叫HEAD。
- 暂存区：stage, 或index。一般存放在 工作区的隐藏目录.git 下的index文件（.git/index）中，所以我们把暂存区有时也叫作索引（index）。

![](http://img.blog.csdn.net/20170215220127324?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMDY5NzM5NA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

我们向版本库添加文件的时候，一般会操作 “git add” 和“git commit”两个步骤。

1.git add 命令：当工作区修改文件或新增时，执行此命令，将文件修改添加到暂存区。

2.git commit 命令：把暂存区的内容提交到当前分支。暂存区的目录树写到版本库（对象库）中，master 分支会做相应的更新。即 master 指向的目录树就是提交时暂存区的目录树。

tree，或者叫文件树，有以下几个特点：
- 每一次commit，都会生成一个全新的tree
- 通过tree的id，可以以递归的方式追踪到下面的所有文件
- 只存在于版本库。暂存区没有tree的概念，工作区也没有tree的概念，因为暂存区和工作区都只有一个当前版本，而git版本库则保存这每一个版本（或者叫每一个提交）时对应的文件树。

其实可以把tree理解为一个文件夹，这个文件夹中包含了其他的文件和子文件夹，然后子文件夹有可以包含其他文件和子子文件夹，只是，这里面提到的所有文件，都是一个引用。如果我们再修改一下a.txt并提交，git会再生成一个新的文件树并关联到这个提交，这个树中的a.txt将会指向一个新的id，而b.txt文件由于没有修改，所有id不变，也就是还指向原来的文件位置。这样做可以优化存储空间。

## HEAD是什么
You can think of the HEAD as the "current branch". When you switch branches with git checkout, the HEAD revision changes to point to the tip of the new branch.

It is possible for HEAD to refer to a specific revision that is not associated with a branch name. This situation is called a detached HEAD.



