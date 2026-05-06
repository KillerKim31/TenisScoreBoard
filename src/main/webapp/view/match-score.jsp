<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Табло текущего матча</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
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
                <h1>Табло текущего теннисного матча</h1>
                <div class="current-match-image"></div>
                <section class="score">
                    <table class="table">
                        <thead class="result">
                            <tr>
                                <th class="table-text">Игрок</th>
                                <th class="table-text">Сеты</th>
                                <th class="table-text">Геймы</th>
                                <th class="table-text">Очки</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="player1">
                                <td class="table-text">${currentMatch.getFirstPlayer().getName()}</td>
                                <td class="table-text">${currentMatch.matchScore.getCountWonSetsPlayer(0)}</td>
                                <td class="table-text">${currentMatch.matchScore.getCurrentSet().getPlayerScore(0)}</td>
                                <td class="table-text">${currentMatch.matchScore.getCurrentGameScore(0)}</td>
                                <td class="table-text">
                                    <div>
                                        <form class="score-frm" method="post">
                                            <c:if test="${currentMatch.getFirstPlayer() != currentMatch.getWinner()
                                                and currentMatch.getSecondPlayer() != currentMatch.getWinner()}">
                                                <button class="score-btn" name="winner_index" value="0">Счет</button>
                                            </c:if>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr class="player2">
                                <td class="table-text">${currentMatch.getSecondPlayer().getName()}</td>
                                <td class="table-text">${currentMatch.matchScore.getCountWonSetsPlayer(1)}</td>
                                <td class="table-text">${currentMatch.matchScore.getCurrentSet().getPlayerScore(1)}</td>
                                <td class="table-text">${currentMatch.matchScore.getCurrentGameScore(1)}</td>
                                <td class="table-text">
                                    <form class="score-frm" method="post">
                                        <c:if test="${currentMatch.getFirstPlayer() != currentMatch.getWinner()
                                            and currentMatch.getSecondPlayer() != currentMatch.getWinner()}">
                                            <button class="score-btn" name="winner_index" value="1">Счет</button>
                                        </c:if>
                                    </form>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <c:if test="${currentMatch.getFirstPlayer() == currentMatch.getWinner()
                            or currentMatch.getSecondPlayer() == currentMatch.getWinner()}">
                        <div class="winner-div">
                            <div class="table-result-title">
                                <p class="result-title">ПОБЕДИТЕЛЬ</p>
                            </div>
                            <div class="table-result">
                                <p>${currentMatch.winner.name}</p>
                            </div>
                            <div style="width: 100%">
                                <form action="end-match" method="post">
                                    <input type="hidden" value="${currentMatch.uuid}" name="uuid">
                                    <button type="submit" class="card_button">Завершить</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </section>
            </div>
        </main>

        <footer>
            <div class="footer">
                <p>&copy; Демонстрационный проект TennisScoreBoard для ведения теннисных матчей</p>
            </div>
        </footer>
    </body>
</html>