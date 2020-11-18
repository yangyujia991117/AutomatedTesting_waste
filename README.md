# AutomatedTesting2020

#### 181250175 杨雨佳

- 选题方向：经典自动化测试

- 文件夹介绍：
  - Demo：存放testSelection.jar
  - Report：存放五张类依赖图和方法依赖图，1-ALU、2-DataLog、3-BinaryHeap、4-NextDay、5-MoreTriangle各一张类依赖图和一张方法依赖图
  - Project：存放项目代码， 项目根目录为AutomatedTesting，程序入口为src/main/java下Start类的main函数

- 内容简介：使用WALA，新建以下几个类，实现类级/方法级测试用例选择：
  - Start：程序入口、分析输入参数、初始化
  - SomeClass：构建分析域
  - MakeGraph：使用WALA带的方法来遍历项目、生成依赖关系图
  - Selector：根据变更文件和依赖关系图进行测试用例选择
  - Graph：依赖图类
  - ClassMethodPair：class-method对类，在该项目中用于存放所有测试用例的class-method对