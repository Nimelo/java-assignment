1) Application uses MVC (Model View Controller) structure pattern.
2) Application serialize and deserialize last result of computation.
	a) It is possible to serialzie empty result.
	b) Serialized are vector, matrix and result of opertaion.
	c) Although LU decomposition is also serialized after clicking button that corresponds to the last invoked operation will be performed once more on already calculated LU Decomposition. This approach was taken because author thought that it is more logical.
3) Exception handling and error messages are more precised than it was said in assignment paper.
4) GUI was designed using InetlliJ thats why there is no source code for it.
	a) Source code is created and compiled during building of the artifact.
	b) Source code can be generated from .form file (MainPanel.form)