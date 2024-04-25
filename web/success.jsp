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

            button.logout:hover,
            header button:hover,
            footer button:hover {
                background-color: #D3FCCC;
                color: #1E1E25;
            }

            button.logout:active,
            header button:active,
            footer button:active {
                background-color: #6A8C8C;
                color: #1E1E25;
                transition: background-color 0.01s ease-in-out, color 0.01s ease-in-out;
            }

            button.logout,
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

            div.logout {
                display: flex;
                justify-content: center;
                align-items: center;
                user-select: none;
            }

            div.logout img {
                max-width: 300px;
            }

            h1.logout {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 3em;
                margin-top: -5px;
            }

            h2.logout {
                display: flex;
                justify-content: center;
                align-items: center;
                color: #373944;
                user-select: none;
                font-size: 2em;
                margin-top: -20px;
                white-space: nowrap;
            }

            .body {
                justify-content: center;
                align-items: center;
                margin: 0;
            }

            button.logout {
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
                margin-top: 30px;
            }

            img {
                -webkit-user-drag: none;
                pointer-events: none;
            }
        </style>
        <title>Success</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <% response.setHeader("Cache-Control", "no-cache, no-store,must - revalidate"); // HTTP 1.1. 
            response.setDateHeader("Expires", 0); // Proxies. 
            String username = null;
            String role = null; // Check if the username is null before getting the username and role
            if (session == null
                    || session.getAttribute("username") == null) {
                response.sendRedirect("error_session.jsp");
            } else {
                username = (String) session.getAttribute("username");
                role = (String) session.getAttribute("role");
            }%>
        <main id="main">
            <header>
                <span>
                    <button id="title">
                        <img src="https://i.imgur.com/blchS4w.png" alt="Icon" />
                        ${applicationScope.header}
                    </button>
                    <h2>${applicationScope.hiddenheader}</h2>
                </span>
            </header>

            <div class="body">
                <div class="logout">
                    <img src="https://i.imgur.com/vokvwP8.png" alt="Login Image" />
                </div>

                <h1 class="logout">Welcome <%= username%>!</h1>
                <h2 class="logout">You are logged in as <%= role%>.</h2>

                <div class="logout">
                    <form action="LogoutServlet" method="POST">
                        <button class="logout" id="logout" type="submit">LOGOUT</button>
                    </form>
                </div>
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
