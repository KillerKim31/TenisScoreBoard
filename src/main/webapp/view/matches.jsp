<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Завершенные матчи</title>
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

        <section>
            <div class="container">
                <h1>Завершенные матчи</h1>
                <div class="input-container">
                    <form action="" method="get" class="frm-filter">
                        <input type="text" name="filter_by_player_name" placeholder="М. Сафин" class="input-filter">
                        <div>
                            <button class="btn-filter" type="submit">Фильтровать</button>
                        </div>
                    </form>
                </div>
                <p>Записей: ${totalItems}</p>

                <table class="table-matches">
                    <tr>
                        <th>ID</th>
                        <th>Игрок 1</th>
                        <th>Игрок 2</th>
                        <th>Победитель</th>
                    </tr>
                    <c:forEach var="match" items="${matches}">
                        <tr>
                            <td class="col-identity">${match.id}</td>
                            <td>${match.player1.name}</td>
                            <td>${match.player2.name}</td>
                            <td><span class="winner-name-td">${match.winner.name}</span></td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="pagination">
                    <a class="prev" href="?page=${currentPage - 1}"> < </a>
                    <p>Стр. ${currentPage + 1} из ${totalPages}</p>
                    <a class="next" href="?page=${currentPage + 1}"> > </a>
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