
const calendarBody = document.getElementById('calendar-body');
const monthYear = document.getElementById('month-year');
const prev = document.getElementById('prev');
const next = document.getElementById('next');
const createReservationButton = document.getElementById('create-reservation');

const today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let selectedDate = null; // 현재 선택된 날짜

const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

function formatDate(date) {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = (d.getMonth() + 1).toString().padStart(2, '0');
    const day = d.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}
function drawBookedMeeting() {
    console.log(formatDate(selectedDate));
}

function generateCalendar(month, year) {
    calendarBody.innerHTML = '';
    monthYear.innerHTML = `${months[month]} ${year}`;

    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = 32 - new Date(year, month, 32).getDate();

    let date = 1;
    for (let i = 0; i < 6; i++) {
        const row = document.createElement('tr');

        for (let j = 0; j < 7; j++) {
            const cell = document.createElement('td');

            if (i === 0 && j < firstDay) {
                cell.innerHTML = '';
            } else if (date > daysInMonth) {
                break;
            } else {
                cell.innerHTML = date;
                cell.dataset.date = `${year}-${month + 1}-${date}`; // 날짜 데이터를 data- 속성에 저장
                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.classList.add('today');
                }
                if (selectedDate === cell.dataset.date) {
                    cell.classList.add('selected-date'); // 현재 선택된 날짜 셀에 클래스 추가
                }
                cell.addEventListener('click', () => {
                    // 기존 선택된 날짜 셀에서 클래스 제거
                    const previousSelectedCell = document.querySelector('.selected-date');
                    if (previousSelectedCell) {
                        previousSelectedCell.classList.remove('selected-date');
                    }

                    // 새로운 선택된 날짜 셀에 클래스 추가
                    cell.classList.add('selected-date');
                    selectedDate = cell.dataset.date;

                    const todayCell = document.querySelector(`td[data-date="${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}"]`);

                    if (todayCell.dataset.date === selectedDate) {
                        todayCell.classList.remove('today');
                    } else {
                        todayCell.classList.add('today');
                    }

                    // 여기에 클릭한 날짜에 대해 추가 작업을 수행할 수 있음
                    drawBookedMeeting();
                });
                date++;
            }
            row.appendChild(cell);
        }
        calendarBody.appendChild(row);
    }
}

prev.addEventListener('click', () => {
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    generateCalendar(currentMonth, currentYear);
});

next.addEventListener('click', () => {
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth === 11) ? 0 : currentMonth + 1;
    generateCalendar(currentMonth, currentYear);
});

createReservationButton.addEventListener('click', (event) => {
    if (selectedDate) {
        createReservationButton.href = `/meeting/new?date=${selectedDate}`;
    } else {
        event.preventDefault();
        alert('날짜를 선택하세요.');
    }
});

generateCalendar(currentMonth, currentYear);
