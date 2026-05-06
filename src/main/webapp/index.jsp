<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Большой теннис</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
    </head>

    <body>
        <header>
            <section class="nav-header">
                <div class="brand">
                    <span class="logo-text">TenisScoreBoard</span>
                </div>
            </section>
        </header>

        <section>
            <div class="container">
                <h1>Добро пожаловать на проект TenisScoreBoard</h1>
                <p>Управляйте вашими матчами по теннису и сохраняйте их.</p>
                <div class="welcome-image"></div>
                <div class="form-container center">
                    <a class="homepage-action-button" href="new-match">
                        <button class="btn start-match">Начать новый матч</button>
                    </a>
                    <a class="homepage-action-button" href="matches">
                        <button class="btn view-results">Завершенные матчи</button>
                    </a>
                </div>
            </div>
        </section>

        <footer>
            <div class="footer">
                <p>&copy; Демонстрационный проект TennisScoreBoard для ведения теннисных матчей</p>
            </div>
        </footer>
    </body>
</html>
