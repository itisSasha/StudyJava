package web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //サーバがブラウザにキャッシュ要らないことを通知
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        // メモリの中に認証コードの背景サイズを定義
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Graphicsを取得
        Graphics g = image.getGraphics();
        // 背景色をグレーに定義する
        g.setColor(Color.gray);
        // 背景を色で塗りつぶしする
        g.fillRect(0, 0, width, height);

        // 認証コードを生成する
        String checkCode = getCheckCode();
        // 認証コードをHttpSessionの中に入れる
        request.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        // 色を白に定義する
        g.setColor(Color.white);
        // 背景に認証コードを書く
        g.drawString(checkCode, 15, 25);

        // メモリの中にある認証コードの図をFEに表示
        ImageIO.write(image, "PNG", response.getOutputStream());

    }
        // getCheckCode()を定義する
        private String getCheckCode () {
            String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int size = base.length();
            Random ran = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                // 0-size-1のランダム数字を生成
                int index = ran.nextInt(size);
                // 値を取得
                char c = base.charAt(index);
                // StringBufferのなかに挿入する
                sb.append(c);
            }
            return sb.toString();
        }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
