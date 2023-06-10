const {Client} = require('pg');
const db = new Client({
    user: 'rayhanakbararrizky',
    host: 'ep-rapid-grass-406926.ap-southeast-1.aws.neon.tech',
    database: 'taskmaster',
    password: 'OlIx73ouvGjP',
    port: 5432,
    sslmode: 'require',
    ssl: true,
});


db.connect((err)=> {
    if(err){
        console.log(err)
        return
    }
    console.log('Database berhasil terkoneksi.')
});

module.exports = db;