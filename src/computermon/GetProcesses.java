package computermon;

/**
 *
 * @author mucyo miller
 * this will handle getting running process 
 * and passing it to save function to
 * save it into Firebase database
 */


import com.google.gson.Gson;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.Native; 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.*;

public class GetProcesses {
        public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();
    Map<String,String> data = new HashMap<String, String>();
    public GetProcesses() throws IOException{
        Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();          

        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
        try  {
            while (kernel32.Process32Next(snapshot, processEntry)) {             
                System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile)+"\t");
                data.put(processEntry.th32ProcessID.toString(),Native.toString(processEntry.szExeFile));
            }
        }
        finally {
            kernel32.CloseHandle(snapshot);
            String jsonString = new Gson().toJson(data);
            System.out.println(jsonString);
            
            
            post("https://computermon-b9477.firebaseio.com/processes.json",jsonString);
        }
    }

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(JSON, json);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  Response response = client.newCall(request).execute();
  return response.body().string();
}
}
