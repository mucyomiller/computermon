package computermon;




import com.google.gson.Gson;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.Native; 
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
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
                //System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile)+"\t");
                data.put(processEntry.th32ProcessID.toString(),Native.toString(processEntry.szExeFile));
            }
        }
        finally {
            kernel32.CloseHandle(snapshot);
                 InetAddress ip = InetAddress.getLocalHost(); 

             NetworkInterface network = NetworkInterface.getByInetAddress(ip);

             byte[] mac = network.getHardwareAddress();

             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < mac.length; i++) {
           sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
         }
             System.out.println("mac is:"+sb.toString());
            
             Process p =new Process(sb.toString(),data);
               String jsonString = new Gson().toJson(p);
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
