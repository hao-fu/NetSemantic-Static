package fu.hao.trust.staticAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fu.hao.trust.data.Results;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;

public class RespAnalysisTest {
	final String TAG = "test";

	String[] args = new String[2];

	public static void main(String[] args) {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/DroidKungFu2/aead12ec6d451b9a27831c8bbdba0f1267f59105";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void DroidKungFu1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/DroidKungFu1/881ee009e90d7d70d2802c3193190d973445d807";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void DroidDreamLight() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/DroidDreamLight/7656cd2159882c165b28ad5ba9e523a34357cf6d/";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void KMin() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/KMin/3726cfddc691cb72e7ed6b059bf943ad9b4d1774/";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void GoldDream() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/GoldDream/1034b746a26e236b9a623a5cebfb67dde52a1d8a/";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void BaseBridge() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/BaseBridge/32d8d9086f95c0d398614380b265f4d528b30026/";//adc8ac7b72a1055438773e7f075028df681d987c/";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Geinimi() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/Geinimi/1353bd14e91a53fa1ee54cd51c1db6918eb9f851";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void AnserverBot() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/AnserverBot/753073a72bc14ee1590e8addd6cb6eeb0a0602f1";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void DroidKunfu4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/DroidKungFu4/e1c2188a69727bf4ec4a5d72319cfe87428c7f35";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void DroidKunfu3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "D:/malwares/DroidKungFu3/255a1b74428b5615d65f39775ec7234e27bd9e74";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void GeneralJava_Exceptions3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/GeneralJava_Exceptions3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void GeneralJava_Exceptions2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/GeneralJava_Exceptions2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void StaticInitialization3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StaticInitialization3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ImplicitFlow2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ImplicitFlow2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Reflection4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Reflection4/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Reflection3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Reflection3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Reflection2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Reflection2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Reflection1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Reflection1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void PublicAPIField2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/PublicAPIField2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void PublicAPIField1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/PublicAPIField1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Parcel1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Parcel1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ApplicationModeling1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ApplicationModeling1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Merge1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Merge1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Looper1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Looper1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_StartActivityForResult1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StartActivityForResult1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_SendSMS() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/SendSMS/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ImplicitFlow1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ImplicitFlow1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_PlayStore1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/PlayStore1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_IMEI1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/IMEI1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ContentProvider1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ContentProvider1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Unregister1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Unregister1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_RegisterGlobal2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/RegisterGlobal2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ServiceLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ServiceLifecycle1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_BroadcastReceiverLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_BroadcastReceiverLifecycle1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ApplicationLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ApplicationLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ApplicationLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ActivityLifecycle4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle4/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Lifecycle_ActivityLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/RegisterGlobal1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_RegisterGlobal1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/RegisterGlobal1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_AnonymousClass1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/AnonymousClass1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ArrayToString1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ArrayToString1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ArrayCopy1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ArrayCopy1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ListAccess1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ListAccess1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_HashMapAccess1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/HashMapAccess1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ArrayAccess2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ArrayAccess2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_ArrayAccess1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ArrayAccess1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_PrivateDataLeak1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/PrivateDataLeak1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Obfuscation1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Obfuscation1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_LogNoLeak() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LogNoLeak/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_InactiveActivity() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/InactiveActivity/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_DirectLeak1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/DirectLeak1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_StringToOutputStream1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StringToOutputStream1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_StringToCharArray1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StringToCharArray1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_StringPatternMatching1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StringPatternMatching1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_StringFormatter1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/StringFormatter1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_Serialization1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Serialization1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_FactoryMethods1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FactoryMethods1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_VirtualDispatch4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/VirtualDispatch4/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_VirtualDispatch3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/VirtualDispatch3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test_VirtualDispatch2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/VirtualDispatch2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_UnresolvableIntent1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/UnresolvableIntent1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_Singletons1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Singletons1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_IntentSource1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/IntentSource1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_IntentSink2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/IntentSink2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_IntentSink1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/IntentSink1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ServiceCommunication1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ServiceCommunication1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ComponentNotInManifest1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ComponentNotInManifest1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_BroadcastTaintAndLeak1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/BroadcastTaintAndLeak1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication8() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication8/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication7() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication7/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication6() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication6/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication5() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication5/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication4/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testICC_ActivityCommunication1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_MultiHandlers1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/MultiHandlers1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_MethodOverride1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/MethodOverride1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button5() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button5/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button4/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button3/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button2/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_JavaThread2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/JavaThread2/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_JavaThread1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/JavaThread1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_Executor1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Executor1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_AsyncTask1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/AsyncTask1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_SharedPreferenceChanged1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/SharedPreferenceChanged1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_AsynchronousEventOrdering1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/AsynchronousEventOrdering1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_EventOrdering1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/EventOrdering1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_FragmentLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FragmentLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_FragmentLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FragmentLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ServiceLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ServiceLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjBroadcastReceiverLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/BroadcastReceiverLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ServiceLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ServiceLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjBroadcastReceiverLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_BroadcastReceiverLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle3/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle4/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle3/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjActivityCommunication2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testIshehui() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/benign/ishehua/com.ishehui.wjh/";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test91deec899c6df09ef68f802979c2697d8a8803be() {
		RespAnalysisTest test = new RespAnalysisTest();

		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/91deec899c6df09ef68f802979c2697d8a8803be/";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void prepare() {
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
	}

	@Test
	public void test0177c2775de43572eb37e5de2803ff57eb297a9f()
			throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/0177c2775de43572eb37e5de2803ff57eb297a9f/"; // droidkungfu";
		RespAnalysisTest test = new RespAnalysisTest();
		test.testRunAnalysis(args);
	}

	@Test
	public void test71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0()
			throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0/"; // droidkungfu";
		RespAnalysisTest test = new RespAnalysisTest();
		test.testRunAnalysis(args);
	}

	public void testRunAnalysis(String[] args) throws IOException {
		Settings.logLevel = 1;
		List<String> apkFiles = new ArrayList<>();
		File apkFile = new File(args[0]);
		if (apkFile.isDirectory()) {
			String[] dirFiles = apkFile.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return (name.endsWith(".apk"));
				}
			});
			for (String s : dirFiles) {
				apkFiles.add(s);
			}
		} else {
			// 获得文件类型
			String extension = apkFile.getName().substring(
					apkFile.getName().lastIndexOf("."));
			if (extension.equalsIgnoreCase(".txt")) {
				BufferedReader rdr = new BufferedReader(new FileReader(apkFile));
				String line = null;
				while ((line = rdr.readLine()) != null)
					apkFiles.add(line);
				rdr.close();
			} else if (extension.equalsIgnoreCase(".apk"))
				apkFiles.add(args[0]);
			else {
				Log.err(TAG, "Invalid input file format: " + extension);
				return;
			}
		}

		for (final String fileName : apkFiles) {
			Results.reset();
			System.gc();
			soot.G.reset();
			final long beforeRun = System.nanoTime();
			Settings.setApkName(fileName);
			Log.msg(TAG, "Begin to analyze: " + fileName);
			Settings.setApkPath(args[0] + File.separator + fileName);
			Settings.platformDir = args[1];

			// App.v();
			RespAnalysis ra = RespAnalysis.v();
			ra.runAnalysis();
			Log.msg(TAG, "Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

}
