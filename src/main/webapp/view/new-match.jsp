<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Новый матч</title>
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
                <div>
                    <nav class="nav-links">
                        <a class="nav-link" href="index.jsp">Главная страница</a>
                    </nav>
                </div>
            </section>
        </header>

        <main>
            <div class="container">
                <div>
                    <h1>Создание нового матча</h1>
                    <div class="new-match-image"></div>
                    <div class="form-container center">
                        <form action="new-match" method="post">
                            <label class="label-player">Игрок 1</label>
                            <input required class="input-player" placeholder="Name" type="text" title="Введите имя" name="player-1">
                            <label class="label-player">Игрок 2</label>
                            <input required class="input-player" placeholder="Name" type="text" title="Введите имя" name="player-2">
                            <label class="label-player">Количество сетов в матче</label>
                            <select required class="form-select" name="match-sets">
                                <option value="">Выбор количества сетов в матче</option>
                                <option value="3">3</option>
                                <option value="5">5</option>
                            </select>
                            <input class="form-button" type="submit" value="Начать">
                        </form>
                    </div>
                </div>
            </div>
        </main>

        <footer>
            <div class="footer">
                <p>&copy; Демонстрационный проект TennisScoreBoard для ведения теннисных матчей</p>
            </div>
        </footer>
    </body>
</html>
