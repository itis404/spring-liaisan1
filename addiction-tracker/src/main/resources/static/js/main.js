function loadAddictions() {
    const container = document.getElementById('addictions-list');
    if (!container) return;

    fetch('/api/addictions')
        .then(response => response.json())
        .then(data => {
            container.innerHTML = '';
            data.forEach(addiction => {
                const div = document.createElement('div');
                div.className = 'addiction-item';
                div.innerHTML = `<strong>${addiction.name}</strong>: ${addiction.description}`;
                container.appendChild(div);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

function calculateSavings() {
    const dailySpend = parseFloat(document.getElementById('dailySpend').value);

    if (!dailySpend || dailySpend <= 0) {
        document.getElementById('savingsResult').textContent = 'Введи сумму в рублях!';
        return;
    }

    fetch('/api/addictions/dollar-rate')
        .then(response => response.text())
        .then(rate => {
            const rateNum = parseFloat(rate.replace(',', '.'));
            const daysCount = parseInt(document.getElementById('daysCount').value) || 1;
            const totalRub = dailySpend * daysCount;
            const totalDollar = (totalRub / rateNum).toFixed(2);
            document.getElementById('savingsResult').textContent =
                `Ты сэкономил ${totalRub} ₽ = ${totalDollar} $`;
        })
        .catch(() => {
            document.getElementById('savingsResult').textContent = 'Ошибка загрузки курса!';
        });
}

document.addEventListener('DOMContentLoaded', function() {
    loadAddictions();
});