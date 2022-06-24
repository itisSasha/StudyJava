package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 認証コードを取得
        String check = request.getParameter("check");

        // sessionを取得
        HttpSession session = request.getSession();
        // sessionから認証コードを取得
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        // 認証コードが一回しか使わないことを保証
        session.removeAttribute("CHECKCODE_SERVER");
        // 比較
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            // 認証コード不一致
            ResultInfo info = new ResultInfo();
            // 登録は失敗
            info.setFlag(false);
            info.setErrorMsg("認証コードは正しくない");
            // infoオブジェクトをjsonに変更
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        // 1. Map形式のデータを取得
        Map<String, String[]> map = request.getParameterMap();

        // 2. データをBeanUtilsの中に保存する
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
           e.printStackTrace();
        } catch (InvocationTargetException e) {
           e.printStackTrace();
        }

        // 3. service層で登録作業を実施する
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        // 4. 判断
        if(flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("登録失敗");
        }

        // infoオブジェクトをjsonにする
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        // jsonデータをFEに返す
        // content-typeを設定
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
