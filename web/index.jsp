<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            * {
                font-family: Tahoma;
            }

            body {
                background: #FFFFFF;
                margin-top: 200px;
                margin-bottom: 7%;
            }

            header {
                width: 100%;
                height: 5%;
                min-height: 60px;
                background-color: #373944;
                align-items: center;
                position: fixed;
                display: flex;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1;
            }

            header button {
                color: #FFFFFF;
                padding-left: 65px;
                padding-top: 10px;
                padding-bottom: 11px;
                padding-right: 30px;
                white-space: nowrap;
                height: 100%;
                background-color: #1E1E25;
                position: relative;
                border-top-right-radius: 100px;
                border-bottom-right-radius: 100px;
                z-index: 1;
                line-height: 1.2;
                user-select: none;
                font-size: 200%;
                font-weight: bold;
                border: none;
            }

            header button img {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                width: 50px;
                margin-left: -55px;
                user-select: none;
            }

            header h2 {
                color: #373944;
                position: absolute;
                margin-top: -45px;
                left: 50%;
                transform: translateX(-50%);
                white-space: nowrap;
                user-select: none;
            }

            button.login:hover,
            header button:hover,
            footer button:hover {
                background-color: #D3FCCC;
                color: #1E1E25;
            }

            button.login:active,
            header button:active,
            footer button:active {
                background-color: #6A8C8C;
                color: #1E1E25;
                transition: background-color 0.01s ease-in-out, color 0.01s ease-in-out;
            }

            button.login,
            header button,
            footer button {
                transition: background-color 0.3s ease-in-out, color 0.1s ease-in-out;
            }

            .carousel-item,
            header h2 {
                transition: background-color 1.3s ease-in-out, color 1.1s ease-in-out;
            }

            footer {
                width: 100%;
                height: 7%;
                background: #1E1E25;
                align-items: center;
                position: fixed;
                display: flex;
                bottom: 0;
                left: 0;
                right: 0;
            }

            .carousel {
                width: 100%;
                overflow: hidden;
                position: absolute;
                top: 0;
                left: 0;
            }

            .carousel-inner {
                display: flex;
                animation: scroll 10s linear infinite;
            }

            .carousel-item {
                padding: 20px;
                text-align: center;
                color: #1E1E25;
                white-space: nowrap;
                margin-top: -5px;
                user-select: none;
            }

            @media (min-width: 1080px) {
                @keyframes scroll {
                    0% {
                        transform: translateX(100%);
                    }
                    100% {
                        transform: translateX(-100%);
                    }
                }

                .carousel-item:hover {
                    color: #D3FCCC;
                }
            }

            @media (min-width: 1280px) {
                header h2:hover {
                    color: #D3FCCC;
                }
            }

            footer button {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                text-align: center;
                color: #FFFFFF;
                max-height: 80px;
                font-size: 100%;
                background-color: #373944;
                border-radius: 100px;
                padding: 15px;
                width: 100px;
                white-space: nowrap;
                z-index: 1;
                user-select: none;
                border: none;
                font-weight: bold;
            }

            div.login {
                display: flex;
                justify-content: center;
                align-items: center;
                user-select: none;
            }

            div.login img {
                max-width: 300px;
                margin-top: -80px;
            }

            .form-container {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .username-input {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                margin-top: 20px;
                font-size: 1.2em;
                margin-left: auto;
                margin-right:auto;
                border-radius: 100px;
                padding: 0.8em 1.6em;
                border: 2px solid #373944;
                width: 180px;
                height: 20px;
                margin: 20px auto;
            }

            .password-input {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 1.2em;
                margin-left: auto;
                margin-right:auto;
                border-radius: 100px;
                padding: 0.8em 1.6em;
                border: 2px solid #373944;
                width: 180px;
                height: 20px;
                margin: 20px auto;
                margin-top: -5px;
            }

            .captcha input {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 1.2em;
                margin-left: auto;
                margin-right:auto;
                border-radius: 100px;
                padding: 0.8em 1.6em;
                border: 2px solid #373944;
                width: 180px;
                height: 20px;
                margin: 20px auto;
                margin-top: -5px;
            }

            .captcha img {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-left: auto;
                margin-right:auto;
                width: 180px;
                height: 40px;
                margin: 20px auto;
                margin-top: -25px;
                -webkit-user-drag: none;
                pointer-events: none;
            }

            .body {
                justify-content: center;
                align-items: center;
                margin: 0;
            }

            button.login {
                text-align: center;
                color: #FFFFFF;
                font-size: 1em;
                background-color: #373944;
                border-radius: 100px;
                padding: 1em 2em;
                width: 250px;
                white-space: nowrap;
                user-select: none;
                border: none;
                font-weight: bold;
                margin-top: -4px;
            }

            img {
                -webkit-user-drag: none;
                pointer-events: none;
            }

        </style>
        <title>HOME</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1. 
            response.setDateHeader("Expires", 0); //Proxies. 
            if (session != null && session.getAttribute("username") != null) {
                session.removeAttribute("username");
            } %>
        <main id="main">
            <header>
                <span>
                    <% String iconUrl = "https://i.imgur.com/blchS4w.png";%>
                    <button id="title">
                        <img src="<%= iconUrl%>" alt="Icon" />
                        ${applicationScope.header}
                    </button>
                    <h2>${applicationScope.hiddenheader}</h2>
                </span>
            </header>

            <div class="body">
                <div class="login">
                    <% String loginImageUrl = "https://i.imgur.com/vokvwP8.png";%>
                    <img src="<%= loginImageUrl%>" alt="Login Image" />
                </div>
                <form action="LoginServlet" method="post">
                    <div class="form-container">
                        <input
                            type="text"
                            id="username"
                            name="username"
                            class="username-input"
                            placeholder="Username"
                            />
                        <input
                            type="password"
                            id="password"
                            name="password"
                            class="password-input"
                            placeholder="Password"
                            />
                    </div>
                    <div class="captcha">
                        <input type="text" name="captcha" placeholder="Captcha" required /><br />
                        <img id="captcha" alt="CAPTCHA Image" /><br />
                    </div>
                    <div class="login">
                        <button class="login" type="submit">LOGIN</button>
                    </div>
                </form>
                <script>
                    // Display the CAPTCHA image from the server
                    window.onload = function () {
                        var captchaImage = document.getElementById("captcha");
                        captchaImage.src = "captcha";
                        captchaImage.onerror = function () {
                            console.error('Failed to load CAPTCHA image from server');
                            captchaImage.alt = 'Failed to load CAPTCHA';
                        };
                    };
                </script>
            </div>

            <footer>
                <div class="carousel">
                    <div class="carousel-inner">
                        <h1 class="carousel-item">
                            <span>${applicationScope.hiddenfooter}</span>
                        </h1>
                    </div>
                </div>
                <button>${applicationScope.footer}</button>
            </footer>
        </main>
    </body>
</html>
