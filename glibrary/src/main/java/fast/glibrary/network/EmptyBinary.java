package fast.glibrary.network;
//			          _ooOoo_  
//	           	     o8888888o  
//                   88" . "88  
//                   (| -_- |)  
//                    O\ = /O  
//                ____/`---'\____  
//              .   ' \\| |// `.  
//               / \\||| : |||// \  
//             / _||||| -:- |||||- \  
//               | | \\\ - /// | |  
//             | \_| ''\---/'' | |  
//            \ .-\__ `-` ___/-. /  
//          ___`. .' /--.--\ `. . __  
//       ."" '< `.___\_<|>_/___.' >'"".  
//      | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//        \ \ `-. \_ __\ /__ _/ .-` / /  
//======`-.____`-.___\_____/___.-`____.-'======  
//                   `=---='  
//
//.............................................  
//               佛祖保佑             永无BUG 

import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.tools.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by GeJianYe on 2017/3/15 0015.
 * Description:
 * Function:
 */

public class EmptyBinary extends BasicBinary {
    final static byte[] content = {0};
    InputStream inputStream;

    public EmptyBinary() {
        super("empty", "emptyDate");
        inputStream = new ByteArrayInputStream(content);
    }

    @Override
    public void cancel() {
        IOUtils.closeQuietly(inputStream);
        super.cancel();
    }

    @Override
    public long getBinaryLength() {
        try {
            return inputStream == null ? 0 : inputStream.available();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return inputStream;
    }
}
