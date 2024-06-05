import { Component, OnInit } from '@angular/core';
import { chartsService } from '../core/services/chartsService';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-acceuil',
  templateUrl: './acceuil.component.html',
  styleUrls: ['./acceuil.component.scss']
})
export class AcceuilComponent implements OnInit {

  countOperationsThisMonth: number;
  countAllAccounts: number;
  countEpargneAccounts: number;
  countCourantAccounts: number;

  constructor(private chartsService: chartsService) { }

  ngOnInit(): void {
    this.chartsService.getCountOperationsThisMonth().subscribe(data => {
      this.countOperationsThisMonth = data;
      this.createPieChart('operationsChart', 'Opérations ce mois-ci', data);
    });
    this.chartsService.getCountAllAccounts().subscribe(data => {
      this.countAllAccounts = data;
      this.createPieChart('accountsChart', 'Tous les Comptes', data);
    });
    this.chartsService.getCountEpargneAccounts().subscribe(data => {
      this.countEpargneAccounts = data;
      if (this.countCourantAccounts !== undefined) {
        this.createCombinedPieChart('combinedChart', ['Epargne Accounts', 'Courant Accounts'], [this.countEpargneAccounts, this.countCourantAccounts]);
      }
    });
    this.chartsService.getCountCourantAccounts().subscribe(data => {
      this.countCourantAccounts = data;
      if (this.countEpargneAccounts !== undefined) {
        this.createCombinedPieChart('combinedChart', ['Epargne Accounts', 'Courant Accounts'], [this.countEpargneAccounts, this.countCourantAccounts]);
      }
    });
  }

  createPieChart(elementId: string, label: string, data: number): void {
    const ctx = document.getElementById(elementId) as HTMLCanvasElement;
    const options: any = {
      plugins: {
        legend: {
          labels: {
            font: {
              family: 'Arial',
              size: 16 
            }
          }
        }
      },
     
    };
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: [label],
        datasets: [{
          label: 'Nombre',
          data: [data],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
          ],
          borderWidth: 1
        }]
      },
      options: options
    });
  }

  createCombinedPieChart(elementId: string, labels: string[], data: number[]): void {
    const ctx = document.getElementById(elementId) as HTMLCanvasElement;
    const options: any = {
      plugins: {
        legend: {
          labels: {
            font: {
              family: 'Arial',
              size: 16 
            }
          }
        }
      },
  
    };
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          label: 'Nombre',
          data: data,
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
          ],
          borderWidth: 1
        }]
      },
      options: options
    });
  }
}
