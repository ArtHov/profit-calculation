(function(document){
    "use strict";

    const form = document.forms[0];
    const date = form.date;
    const amount = form.amount;
    const calculate = document.querySelector('#result');
    const errorContainer = document.querySelector('.error');

    const submitForm = (date, amount, result, error) => {
        const dateValue = date.value;
        const amountValue = amount.value;
        const reqUrl = `http://localhost:8080/api/profit/calculation?date=${dateValue}&amount=${amountValue}`;
        const req = new XMLHttpRequest();

        if (!dateValue.trim() || !amountValue.trim()) {
            return;
        }

        req.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                const value = +JSON.parse(this.response).profit;
                result.value = value;

                error.classList.remove('error-container');

                if (value < 0) {
                    result.className = 'red';
                } else {
                    result.className = 'green';
                }
            }

            if (this.status === 400) {
                error.innerHTML = 'Bad request';
                error.classList.add('error-container');
            }
        };

        req.open("GET", reqUrl, true);
        req.send(null);
    };

    form.addEventListener('submit', () => {
        submitForm(date, amount, calculate, errorContainer);
}, false);

})(document);