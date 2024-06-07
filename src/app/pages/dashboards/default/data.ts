import { ChartType } from './dashboard.model';

const emailSentBarChart: ChartType = {
    chart: {
        height: 340,
        type: 'bar',
        stacked: true,
        toolbar: {
            show: false
        },
        zoom: {
            enabled: true
        }
    },
    plotOptions: {
        bar: {
            horizontal: false,
            columnWidth: '15%',
            endingShape: 'rounded'
        },
    },
    dataLabels: {
        enabled: false
    },
    series: [{
        name: 'Series A',
        data: [44, 55, 41, 67, 22, 43, 36, 52, 24, 18, 36, 48]
    }, {
        name: 'Series B',
        data: [13, 23, 20, 8, 13, 27, 18, 22, 10, 16, 24, 22]
    }, {
        name: 'Series C',
        data: [11, 17, 15, 15, 21, 14, 11, 18, 17, 12, 20, 18]
    }],
    xaxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    },
    colors: ['#556ee6', '#f1b44c', '#34c38f'],
    legend: {
        position: 'bottom',
    },
    fill: {
        opacity: 1
    },
};

const monthlyEarningChart: ChartType = {
    chart: {
        height: 200,
        type: 'radialBar',
        offsetY: -10
    },
    plotOptions: {
        radialBar: {
            startAngle: -135,
            endAngle: 135,
            dataLabels: {
                name: {
                    fontSize: '13px',
                    color: undefined,
                    offsetY: 60
                },
                value: {
                    offsetY: 22,
                    fontSize: '16px',
                    color: undefined,
                    formatter: (val) => {
                        return val + '%';
                    }
                }
            }
        }
    },
    colors: ['#556ee6'],
    fill: {
        type: 'gradient',
        gradient: {
            shade: 'dark',
            shadeIntensity: 0.15,
            inverseColors: false,
            opacityFrom: 1,
            opacityTo: 1,
            stops: [0, 50, 65, 91]
        },
    },
    stroke: {
        dashArray: 4,
    },
    series: [67],
    labels: ['Series A'],
};

const transactions = [
    {
        id: '#SK2540',
        name: '25414785',
        date: '08/05/2024',
        total: '400',
       
        index: 1
    },
    {
        id: '#SK2541',
        name: '54789654',
        date: '08/05/2024',
        total: '380',
       
        index: 2
    },
    {
        id: '#SK2542',
        name: '58744446',
        date: '08/05/2024',
        total: '384',
     
        index: 3
    },
    {
        id: '#SK2543',
        name: '25414526',
        date: '08/05/2024',
        total: '412',
       
        index: 4
    },
    {
        id: '#SK2544',
        name: '25414178',
        date: '08/05/2024',
        total: '404',
    
        index: 5
    },
    {
        id: '#SK2545',
        name: '25414487',
        date: '08/05/2024',
        total: '392',
       
        index: 6
    }
];

const statData = [{
    icon: 'bx bx-copy-alt',
    title: 'Orders',
    value: '1,235'
}, {
    icon: 'bx bx-archive-in',
    title: 'Revenue',
    value: '$35, 723'
}, {
    icon: 'bx bx-purchase-tag-alt',
    title: 'Average Price',
    value: '$16.2'
}];

export { emailSentBarChart, monthlyEarningChart, transactions, statData };
