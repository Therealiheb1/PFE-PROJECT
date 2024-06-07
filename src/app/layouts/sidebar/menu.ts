import { MenuItem } from './menu.model';

export const MENU: MenuItem[] = [
{
  id: 1,
  label: 'MENUITEMS.MENU.TEXT',
  isTitle: true
},
{
  id: 2,
  icon: 'bx-home-circle',
  badge: {
      variant: 'info',
      text: 'MENUITEMS.DASHBOARDS.BADGE',
  },
  label: "Page d'acceuil",
  link: '/acceuil',
  parentId: 2
},
{
  id: 7,
  isLayout: true
},
{
  id: 8,
 
  isTitle: true
},


{
  id: 29,
  icon: 'bx bx-file-find',
 
 
        
          label: 'Liste des comptes',
          link: '/Admin',
          parentId: 29
   
},
{
  id: 7,
  isLayout: true
},
{
  id: 8,
 
  isTitle: true
},


{
  id: 29,
  icon: 'bx bx-transfer-alt',
 
 
        
          label: 'Transaction',
          link: '/tran',
          parentId: 29
   
},
{
  id: 7,
  isLayout: true
},
{
  id: 8,
 
  isTitle: true
},

{
  id: 29,
  icon: 'bx bxs-barcode',
 
 
        
          label: 'Les demande des chequier',
          link: '/cheque',
          parentId: 29
   
},
{
  id: 29,
  icon: 'bx-file',
 
 
        
          label: 'Les demande des chequier',
          link: '/box',
          parentId: 29
   
},


    
];

