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

// Функция для отправки данных с гибкостью метода
async function submitForm(values, method = 'GET') {
    const params = new URLSearchParams(values).toString();
    const response = await fetch(`/lab2-1.0-SNAPSHOT/controller-servlet?${params}`, {
        method: method,
        headers: {'Content-Type': 'application/json;charset=utf-8'}
    });

    if (response.ok) {
        const html = await response.text();
        document.open();
        document.write(html);
        document.close();
    } else {
        throw new Error("Ошибка при получении данных с сервера.");
    }
}

// Переменная для хранения последнего значения Y в координатах
let lastYCoord = null;

// Обработчик кликов по SVG
function handleClick(event) {
    const svg = document.getElementById("plate");
    const point = svg.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;
    const coords = point.matrixTransform(svg.getScreenCTM().inverse());

    const r = document.querySelector('input[name="r"]:checked')?.value;
    const x = (coords.x - 250) / 20;
    let y = (250 - coords.y) / 20;
    y = Math.round(y);

    // Преобразуем Y обратно в координаты
    const currentYCoord = 250 - (y * 20);

    // Проверяем, находится ли курсор в пределах 5 пикселей от последней линии
    if (lastYCoord !== null && Math.abs(currentYCoord - lastYCoord) <= 5) {
        // Если да, отправляем запрос
        const values = {
            x: x.toFixed(2),
            y: y,
            r: r
        };

        try {
            validateFormInput(values);
            submitForm(values).catch(error => alert(error.message));
        } catch (e) {
            alert(e.message);
        }
    } else {
        // Если нет, рисуем новую линию
        drawHorizontalLine(y);
        lastYCoord = currentYCoord; // Обновляем последнее значение Y в координатах
    }
}

function drawHorizontalLine(y) {
    const svg = document.getElementById("plate");
    const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
    line.setAttribute("x1", 0);
    line.setAttribute("y1", 250 - (y * 20)); // Преобразование координаты Y
    line.setAttribute("x2", 500); // Длина линии
    line.setAttribute("y2", 250 - (y * 20));
    line.setAttribute("stroke", "red"); // Цвет линии
    line.setAttribute("stroke-width", 1);

    // Удаляем предыдущую линию, если она существует
    const existingLine = document.getElementById("horizontal-line");
    if (existingLine) {
        svg.removeChild(existingLine);
    }

    line.setAttribute("id", "horizontal-line");
    svg.appendChild(line);
}

// Универсальная обработка формы
async function handleSubmitForm(ev, form) {
    ev.preventDefault();
    const formData = new FormData(form);
    const values = {
        x: formData.get('x'),
        y: formData.get('y'),
        r: formData.get('r')
    };

    const errorDiv = document.getElementById("error");

    try {
        validateFormInput(values);
        errorDiv.hidden = true;
        await submitForm(values);
    } catch (e) {
        errorDiv.hidden = false;
        errorDiv.textContent = e.message;
    }
}

document.getElementById("data-form").addEventListener('submit', (ev) => handleSubmitForm(ev, ev.target));

// Добавляем обработчик клика на SVG
document.getElementById("plate").addEventListener("click", handleClick);

// Автозапуск аудио при первом клике
document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', () => {
        document.getElementById("intro_audio").play();
    }, {once: true});
});

document.addEventListener("DOMContentLoaded", function () {
    let radius = document.querySelector('input[name="r"]:checked').value;
    console.log("Initial radius:", radius);
    updateGraph(radius);

    const radioButtons = document.querySelectorAll('input[name="r"]');
    radioButtons.forEach(radio => {
        radio.addEventListener('change', function () {
            console.log("Changed radius to:", this.value);
            radius = this.value;
            updateGraph(radius);
        });
    });

    function updateGraph(r) {
        const scaleFactor = r / 5; // Масштабируем относительно R = 5
        console.log("Scale factor:", scaleFactor);

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
});
