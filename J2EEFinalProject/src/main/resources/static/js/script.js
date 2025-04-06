document.addEventListener("DOMContentLoaded", function () {
    const chartDataElement = document.getElementById("chartData");

    // Get the data from the element with thymeleaf data in it
    const incomeData = JSON.parse(chartDataElement.getAttribute("data-income") || "[]");
    const expenseData = JSON.parse(chartDataElement.getAttribute("data-expenses") || "[]");
    const labels = JSON.parse(chartDataElement.getAttribute("data-labels") || "[]");

    const ctx = document.getElementById('financeChart').getContext('2d');
    // This uses chart.js, so even though this is JavaScript, this might as well be a weird version of CSS since it's just for styles
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Income',
                    data: incomeData,
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                },
                {
                    label: 'Expenses',
                    data: expenseData,
                    backgroundColor: 'rgba(255, 99, 132, 0.6)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: false
                }
            }
        }
    });
});