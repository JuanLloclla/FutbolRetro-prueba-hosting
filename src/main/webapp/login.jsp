<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="//code.tidio.co/h3vn9dwczska9xxz82te7gjkkgbdyhn8.js" async></script>
        <link href="css/loginCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Login</title>  
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
    </head>
    <body>
        <div class="container">
            <div class="login">
                <div class="logo">
                    <img src="img/logo(Nuevo).png" alt="logo.png"/>
                </div>
                <form action="srvLogin" method="post">
                    <h2>LOGIN</h2>
                    <h3>User:</h3>
                    <input class="controls" type="text" name="txtusuario" placeholder="Usuario" autocomplete="off" required>
                    <h3>Password:</h3>
                    <input class="controls" type="password" name="txtclave" placeholder="Contraseña" autocomplete="off" required>
                    <input class="buttons" type="submit" name="accion" value="INGRESAR">
                    <br>
                    <p class="mensaje">
                    <%
                        String mensaje = (String) request.getAttribute("mensaje");
                        if (mensaje != null) {
                            out.print(mensaje);
                        }
                    %>
                    </p>
                </form>
            </div>
            <div class="publicidad">
                <img src="img/publicidadLogin.webp" alt="loginFondo.jpg"/>
            </div>
        </div>
    </body>
</html>

