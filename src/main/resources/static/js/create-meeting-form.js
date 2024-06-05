function generateTimeOptions() {
    const selectStartTime = document.getElementById('startTime');
    const selectEndTime = document.getElementById('endTime');
    const hours = 24;
    const minutes = 60;
    const interval = 30; // 30분 간격

    for (let hour = 0; hour < hours; hour++) {
        for (let minute = 0; minute < minutes; minute += interval) {
            const time = (hour < 10 ? '0' + hour : hour) + ':' + (minute === 0 ? '00' : minute);
            const startTimeOption = document.createElement('option');
            const endTimeOption = document.createElement('option');
            startTimeOption.value = time;
            startTimeOption.text = time;
            endTimeOption.value = time;
            endTimeOption.text = time;
            selectStartTime.appendChild(startTimeOption);
            selectEndTime.appendChild(endTimeOption);
        }
    }
}

function formatDate(date) {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = (d.getMonth() + 1).toString().padStart(2, '0');
    const day = d.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}

document.addEventListener('DOMContentLoaded', function () {
    generateTimeOptions();

    let selectedDate = document.getElementById('selectedDate').value;
    let dateSpan = document.getElementById('dateSpan').innerText;
    selectedDate = formatDate(selectedDate);
    document.getElementById('dateSpan').innerText = formatDate(dateSpan);

    const startTimeSelect = document.getElementById('startTime');
    const hiddenStartTime = document.getElementById('hiddenStartTime');
    const endTimeSelect = document.getElementById('endTime');
    const hiddenEndTime = document.getElementById('hiddenEndTime');

    startTimeSelect.addEventListener('change', () => {
        const selectedTime = startTimeSelect.value;
        const dateTimeValue = `${selectedDate}T${selectedTime}:00`;
        hiddenStartTime.value = dateTimeValue;
    });

    endTimeSelect.addEventListener('change', () => {
        const selectedTime = endTimeSelect.value;
        const dateTimeValue = `${selectedDate}T${selectedTime}:00`;
        hiddenEndTime.value = dateTimeValue;
    });
});
