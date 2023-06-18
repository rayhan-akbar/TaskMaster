# TaskMaster
A Task Manager App based on Android using NodeJS and PostgreSQL.

---
## Gambaran Umum Program
Sebuah aplikasi pengelolaan tugas dan pengaturan jadwal berbasis android yang dibuat menggunakan ```Android Studio``` untuk Front-End dan juga ```NodeJS``` untuk Back-End dan juga Menggunakan Database berbasis ```PostgreSQL``` yang dibantu dengan layanan Cloud yaitu ```NeonDB```.

---
## Kontributor
Projek ini dibuat personal oleh saya, seorang mahasiswa teknik komputer Universitas Indonesia:
1. [Rayhan Akbar Arrizky](https://github.com/rayhan-akbar)

---
## Postman Documentation Collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/24261759/2s93shzUrc)

---
## Penjelasan mengenai Table pada Program
### 1.  ```users```

Table users adalah table yang berguna untuk menyimpan data ```User```. Attribute dari tabel ini adalah:
```
1. UserID (PK)
2. username
3. Password
```

### 2.  ```group```

Table group adalah table yang berguna untuk menyimpan data ```group```. Attribute dari tabel ini adalah:
```
1. GroupID (PK)
2. Nama_Group
3. Deskripsi_Grup
```

### 3.  ```groupEnrollment```

Table group enrollment adalah table yang berguna untuk menyimpan data  ```enrollment``` masing-masing ```user``` ke dalam masing-masing ```group```. Attribute dari tabel ini adalah:
```
1. UserID (FK)
2. GroupID (FK)
3. Tanggal_Masuk
```

### 4.  ```tugasIndividu```

Table Tugas Individu adalah table yang berguna untuk menyimpan data  ```Tugas``` yang dibuat oleh ```user```. Attribute dari tabel ini adalah:
```
1. TugasIndividuID (PK)
2. UserID (FK)
3. Nama_Tugas
4. Deskripsi_Tugas
5. Tanggal_Pengerjaan
6. Status
```

### 5.  ```tugasKelompok```

Table Tugas Individu adalah table yang berguna untuk menyimpan data  ```Tugas``` yang dibuat oleh ```group```. Attribute dari tabel ini adalah:
```
1. TugasGrupID (PK)
2. GroupID (FK)
3. Nama_Tugas
4. Deskripsi_Tugas
5. Tanggal_Pengerjaan
6. Status
```

---
# Tampilan Relation Table dan UML
```Table Relational atau ERD:```

![alt text!](https://github.com/rayhan-akbar/TaskMaster/blob/main/assets/ER_Diagram.png)

```UML:```

![alt text!](https://github.com/rayhan-akbar/TaskMaster/blob/main/assets/UML_Diagram.png)


---
# Tampilan FlowChart

```FlowChart:```

![alt text!](https://github.com/rayhan-akbar/TaskMaster/blob/main/assets/AppFlowchart.png)

---