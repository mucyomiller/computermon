
package computermon;

import java.util.Map;

/**
 *
 * @author Sam
 */
public class Process {
       String mac;
    Map<String,String> process;

    public Process(String mac,Map<String, String> process) {
        this.process = process;
        this.mac = mac;
    }

    public Map<String, String> getProcess() {
        return process;
    }

    public void setProcess(Map<String, String> process) {
        this.process = process;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

   
    
    
}
