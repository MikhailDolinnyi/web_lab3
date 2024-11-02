// Оборачиваем код в обработчик события DOMContentLoaded, чтобы убедиться, что элементы DOM загружены
document.addEventListener("DOMContentLoaded", function () {


    // Классы валидации и функции валидации
    class InvalidValueException extends Error {
        constructor(message) {
            super(message);
            this.name = "InvalidValueException";
        }
    }

    class Validator {
        validate(value) {
            throw new Error("Метод validate() нужно переопределить");
        }
    }

    class XValidator extends Validator {
        validate(value) {
            if (isNaN(value)) {
                throw new InvalidValueException("Неверное значение X");
            }

            const decimalPart = String(value).trim().split('.')[1];
            if (decimalPart && decimalPart.length > 15) {
                throw new InvalidValueException("Слишком много знаков после запятой");
            }

            const x = Number(value);
            if (x < -3 || x > 3) {
                throw new InvalidValueException("Число X не входит в диапазон");
            }

            return true;
        }
    }

    class YValidator extends Validator {
        validate(value) {
            if (isNaN(value)) {
                throw new InvalidValueException("Неверное значение Y");
            }

            const y = Number(value);
            if (y < -4 || y > 4) {
                throw new InvalidValueException("Число Y не входит в диапазон");
            }
            return true;
        }
    }

    class RValidator extends Validator {
        validate(value) {
            if (!value) {
                throw new InvalidValueException("Пожалуйста, выберите значение R");
            }
            return true;
        }
    }

    const xValidator = new XValidator();
    const yValidator = new YValidator();
    const rValidator = new RValidator();

    function validateFormInput(values) {
        xValidator.validate(values.x);
        yValidator.validate(values.y);
        rValidator.validate(values.r);
    }

    function handleClick(event) {
        const svg = document.getElementById("plate");
        const point = svg.createSVGPoint();
        point.x = event.clientX;
        point.y = event.clientY;
        const coords = point.matrixTransform(svg.getScreenCTM().inverse());

        const r = document.querySelector('input[name="data-form\:rSelect"]:checked')?.value;
        const x = (coords.x - 250) / 20;
        let y = (250 - coords.y) / 20;
        y = Math.round(y);

        try {
            validateFormInput({x: x.toFixed(2), y: y, r: r});

            // Устанавливаем значения в форме
            document.querySelector('input[name="data-form:x"]').value = x.toFixed(2);
            document.querySelector('input[name="data-form:y"]').value = y;
            document.querySelector('input[name="data-form:rSelect"][value="' + r + '"]').checked = true;

            // Отправляем форму через стандартное JSF-событие
            validateAndSubmitForm();
        } catch (e) {
            alert(e.message);
        }
    }

    let allowAjaxRequest = false;

    window.validateAndSubmitForm = function () {
        const form = document.getElementById("data-form");
        const formData = new FormData(form);
        const values = {
            x: formData.get('data-form:x'),
            y: formData.get('data-form:y'),
            r: formData.get('data-form:rSelect')
        };

        const errorDiv = document.getElementById("error");

        try {
            validateFormInput(values);
            errorDiv.hidden = true;
            allowAjaxRequest = true; // Валидация прошла, разрешаем отправку
            return true;
        } catch (e) {
            errorDiv.hidden = false;
            errorDiv.textContent = e.message;
            allowAjaxRequest = false; // Валидация не прошла, запрещаем отправку
            return false;
        }
    }


    // Привязываем обработчик события для клика по SVG
    document.getElementById("plate").addEventListener("click", handleClick);

    // Обновление графика при изменении R
    let radius = document.querySelector('input[name="data-form\:rSelect"]:checked')?.value;
    if (radius) {
        updateGraph(radius);
    }

    const radioButtons = document.querySelectorAll('input[name="data-form\:rSelect"]');
    radioButtons.forEach(radio => {
        radio.addEventListener('change', function () {
            radius = this.value;
            updateGraph(radius);
        });
    });

    function updateGraph(r) {
        const scaleFactor = r / 5;

        document.getElementById("rect").setAttribute("width", 49 * scaleFactor);
        document.getElementById("rect").setAttribute("height", 99 * scaleFactor);
        document.getElementById("rect").setAttribute("x", 250 - 49 * scaleFactor);
        document.getElementById("rect").setAttribute("y", 250 + 1);

        document.getElementById("arc").setAttribute("d", `M ${250 + 100 * scaleFactor} 251 A ${75 * scaleFactor} ${100 * scaleFactor} 400 0 1 251 ${250 + 100 * scaleFactor} L 251 251 Z`);

        document.getElementById("triangle").setAttribute("points", `251,249 251,${250 - 50 * scaleFactor} ${250 + 100 * scaleFactor},249`);

        document.getElementById("mark-neg-rx").setAttribute("x1", 250 - 100 * scaleFactor);
        document.getElementById("mark-neg-rx").setAttribute("x2", 250 - 100 * scaleFactor);

        document.getElementById("mark-rx").setAttribute("x1", 250 + 100 * scaleFactor);
        document.getElementById("mark-rx").setAttribute("x2", 250 + 100 * scaleFactor);

        document.getElementById("mark-ry").setAttribute("y1", 250 - 100 * scaleFactor);
        document.getElementById("mark-ry").setAttribute("y2", 250 - 100 * scaleFactor);

        document.getElementById("mark-neg-ry").setAttribute("y1", 250 + 100 * scaleFactor);
        document.getElementById("mark-neg-ry").setAttribute("y2", 250 + 100 * scaleFactor);

        document.getElementById("label-neg-rx").setAttribute("x", 250 - 120 * scaleFactor);
        document.getElementById("label-rx").setAttribute("x", 250 + 103 * scaleFactor);

        document.getElementById("label-neg-ry").setAttribute("y", 250 + 110 * scaleFactor);
        document.getElementById("label-ry").setAttribute("y", 250 - 95 * scaleFactor);
    }


    function drawPoints() {
        console.log("drawPoints called");
        const svg = document.getElementById("plate");
        // Очищаем старые точки перед отрисовкой новых
        svg.querySelectorAll(".data-point").forEach(point => point.remove());

        // Находим все точки в #points-data
        const points = document.querySelectorAll("#points-data .point");

        points.forEach(point => {
            // Извлекаем данные из атрибутов
            const x = parseFloat(point.getAttribute("data-x"));
            const y = parseFloat(point.getAttribute("data-y"));
            const result = point.getAttribute("data-result") === "true";

            // Преобразуем координаты для SVG-системы (центр 250,250 и масштаб)
            const svgX = 250 + x * 20; // Пример масштабирования, если 1 единица = 20 пикселей
            const svgY = 250 - y * 20;

            // Создаем круг для точки
            const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
            circle.setAttribute("cx", svgX);
            circle.setAttribute("cy", svgY);
            circle.setAttribute("r", 2);
            circle.setAttribute("fill", result ? "green" : "red");
            circle.classList.add("data-point");

            // Добавляем точку на svg
            svg.appendChild(circle);
        });
    }


    // MutationObserver для отслеживания изменений в <td id="result">
    const resultElement = document.getElementById("result");
    if (resultElement) {
        const observer = new MutationObserver(function () {
            drawPoints();
        });

        observer.observe(resultElement, {
            childList: true,
            subtree: true
        });
    }

    drawPoints(); // Изначальная отрисовка точек





});
