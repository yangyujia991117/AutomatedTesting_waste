import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Start {

  public static void main(String args[])
      throws IOException, WalaException, IllegalArgumentException, InvalidClassFileException,
          CancelException {
    /*分析输入参数的过程*/
    String type=args[0];//取得本次进行测试用例选择的级别 -c:类级，-m:方法级
    String path = args[1];//项目路径
    String change_info_path = args[2];//变更文件路径
    System.out.println("选择类型为"+type+",\n项目路径为"+path+"\n变更文件路径为"+change_info_path);

    /*找到项目中所有.class文件的过程*/
    ArrayList<String> file_paths = new ArrayList<>(); // 该链表存所有.class文件路径
    initialize(path, file_paths); // 遍历找到所有.class文件

    /* 构建分析域（AnalysisScope）对象scope的过程 */
    SomeClass someClass = new SomeClass();
    AnalysisScope scope = someClass.loadClass(file_paths);

    /*创建类级、方法级依赖图的过程*/
    MakeGraph makeGraph = new MakeGraph();
    ArrayList<Object> result = makeGraph.analysis(scope, "xxx");

    /*取得之前创建的两个依赖图和测试用例的class-method对集，用于测试用例选择*/
    Graph classGraph = (Graph) result.get(0);//取得类依赖图
    Graph methodGraph = (Graph) result.get(1);//取得方法依赖图
    ArrayList<ClassMethodPair> classMethodPairs = (ArrayList<ClassMethodPair>) result.get(2);//取得测试用例的class-method对集

    /*根据依赖图进行测试用例选择的过程*/
    Selector selector =
        new Selector(
            change_info_path, classGraph, methodGraph, classMethodPairs);
    selector.decodeChange();//解析文件变更
    if (type.equals("-c")) {
      selector.exec_class_select(); // 执行类级测试用例选择
    } else if (type.equals("-m")) {
      selector.exec_method_select(); // 执行方法级测试用例选择
      }
  }

  // 递归遍历找到所有的.class文件
  public static void initialize(String path, ArrayList<String> file_paths) {
    File file = new File(path);
    if (file.exists()) {
      File[] files = file.listFiles();
      if (null != files) {
        for (File file2 : files) {
          String s = file2.getAbsolutePath();
          if (file2.isDirectory()) {
            initialize(file2.getAbsolutePath(), file_paths);
          } else {
            if (s.endsWith(".class")) {
              file_paths.add(s);
            }
          }
        }
      }
    } else {
      System.out.println("文件不存在!");
    }
  }
}
