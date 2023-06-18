const res = require('express/lib/response');
const { use } = require('passport/lib');
const db = require('../configs/db.config');
const helper = require('../utils/helper');
const { describe } = require('node:test');
const { group } = require('console');

async function login (tm){
    const {username, password} = tm;
    const query = `SELECT * FROM users WHERE username = '${username}'`;
    const result = await db.query(query);
    if(result.rows.length === 0){
        return {
            message: 'User not found'
        }
    }else{
        const user = result.rows[0];
        const comparePass = await helper.comparePassword(password, user.password);
        if(comparePass){
            return {
                message: 'Login successful',
                user
            }
        }else{
            return {
                message: 'Password is not correct'
            }
        }
    }
}

async function register (tm){
    const {username, password, email} = tm;
    const pass = await helper.hashPassword(password);
    const query = `INSERT INTO users (username, password) VALUES ('${username}', '${pass}')`;
    const result = await db.query(query);
    if(result.rowCount === 1){
        return {
            message: 'User Created'
        }
    }else{
        return{
            message: 'Error'
        } 
    }
}

async function showUser (user){
    if(user){
        const query = `SELECT * FROM users WHERE UserID=${user.userID}`;
        const result = await db.query(query);
        if(result.rowCount === 1){
            return {
                message: 'User found',
                user : result.rows
            }
        }else{
            return {
                message: 'User not found'
            }
        }
    }else{
        return {
            message: 'User not found!'
        }
    }
}

async function deleteUser (user){
    if(user){
        const query = `DELETE FROM users WHERE UserID=${user.UserID}`;
        const result = await db.query(query);
        if(result.rowCount === 1){
            return {
                message: 'User deleted'
            }
        }
        else{
            return {
                message: 'User not found'
            }
        }
    }
    else{
        return {
            message: 'USer not logged in'
        }
    }
}

async function addGroup (tm){
    const {Nama_Group, Deskripsi} = tm;
    const query = `INSERT INTO groups (nama_group, deskripsi) VALUES ('${Nama_Group}', '${Deskripsi}')`;
    const result = await db.query(query);
    if(result.rowCount === 1){
        return {
            message: 'Group Created'
        }
    }else{
        return{
            message: 'Error'
        } 
    }
}

async function showGroup (){
    const query = `SELECT * FROM groups`;
    const result = await db.query(query);
    if(result.rowCount){
        return {
            message: 'Groups found',
            showGroup : result.rows
        }
    }else{
        return {
            message: 'no Groups found'
        }
    }

    }


async function searchGroupByName (tm){
    const {Nama_Group} = tm;
    if(group){
        const query = `SELECT * FROM groups WHERE nama_group LIKE '%${Nama_Group}%'`;
        const result = await db.query(query);
        if(result.rowCount){
            return {
                message: 'Group found',
                searchGroupByName : result.rows
            }
        }else{
            return {
                message: 'group not found'
            }
        }
    }else{
        return {
            message: 'group not found'
        }
    }
}

async function searchGroupByID (tm){
    const {GroupID} = tm;

    if(group){
        const query = `SELECT * FROM groups WHERE groupID = ${GroupID}`;
        const result = await db.query(query);
        if(result.rowCount){
            return {
                message: 'Group found',
                searchGroupByID : result.rows
            }
        }else{
            return {
                message: 'group not found'
            }
        }
    }else{
        return {
            message: 'group not found'
        }
    }
}


async function enrollGroup (tm){
    const {GroupID, UserID} = tm;
    const query1 = `SELECT * FROM GroupEnrollment WHERE UserID=${UserID} AND GroupID=${GroupID}`;
    const result1 = await db.query(query1);
    if(result1.rowCount > 0){
        return{
            message: 'User already enrolled in this group'
        }
    }
    else{
        const query = `INSERT INTO GroupEnrollment (UserID, GroupID) VALUES (${UserID}, ${GroupID})`;
        const result = await db.query(query);
        if(result.rowCount === 1){
            return {
                message: 'Group Enrolled'
            }
        }else{
            return{
                message: 'Error'
            } 
        }
    }
}

async function unenrollGroup (tm, user){
    const {GroupID} = tm;
    if(user){
        const query = `DELETE FROM GroupEnrollment WHERE UserID=${user.UserID} AND GroupID=${GroupID}`;
        const result = await db.query(query);
        if(result.rowCount === 1){
            return {
                message: 'Group Unenrolled'
            }
        }else{
            return{
                message: 'Error'
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function showEnrollment(user){
    if(user){
        const query = `SELECT * FROM GroupEnrollment NATURAL JOIN groups WHERE UserID=${user.UserID}`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Enrollment found',
                showEnrollment : result.rows
            }
        }else{
            return{
                message: 'No enrollment found'
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function showGroupMembers(user){
    if(user){
        const query = `SELECT userid, username, tanggal_masuk FROM GroupEnrollment NATURAL JOIN users WHERE groupID=${user.GroupID}`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Group Member found',
                showGroupMembers : result.rows
            }
        }else{
            return{
                message: 'No enrollment found'  
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}


async function addIndividualTask (tm, user){
    const {Nama_Tugas, Deskripsi_tugas, Tanggal_pengerjaan} = tm;
    if(user){
        const query = `INSERT INTO TugasIndividu (UserID, Nama_Tugas, Deskripsi_Tugas, Tanggal_pengerjaan) VALUES ('${user.UserID}', '${Nama_Tugas}', '${Deskripsi_tugas}', '${Tanggal_pengerjaan}')`;
        const result = await db.query(query);
        if(result.rowCount === 1){
            return {
                message: 'Individual Task Created'
            }
        }else{
            return{
                message: 'Error'
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function addGroupTask (tm){
    const {GroupID, Nama_Tugas, Deskripsi_tugas, Tanggal_pengerjaan} = tm;
    const query = `INSERT INTO TugasKelompok (GroupID, Nama_Tugas, Deskripsi_Tugas, tanggal_pengerjaan) VALUES (${GroupID}, '${Nama_Tugas}', '${Deskripsi_tugas}', '${Tanggal_pengerjaan}')`;
    const result = await db.query(query);
    if(result.rowCount === 1){
        return {
            message: 'Group Task Created'
        }
    }else{
        return{
            message: 'Error'
        } 
    }
}

async function showIndividualTask (user){
    if(user){
        const query = `SELECT * FROM TugasIndividu WHERE UserID=${user.UserID} ORDER BY tanggal_pengerjaan ASC`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Task Found',
                showIndividualTask : result.rows
            }
        }else{
            return{
                message: 'No Task Found' 
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function filterIndividualTaskByStatus (user){
    if(user){
        const query = `SELECT * FROM TugasIndividu WHERE UserID=${user.UserID} AND status='${user.Status}' ORDER BY tanggal_pengerjaan ASC`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Task Found',
                showIndividualTask : result.rows
            }
        }else{
            return{
                message: 'No Task Found' 
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function searchIndividualTaskByName (user){
    if(user){
        const query = `SELECT * FROM TugasIndividu WHERE UserID=${user.UserID} AND nama_tugas LIKE '%${user.Nama_Tugas}%' ORDER BY tanggal_pengerjaan ASC`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Task Found',
                showIndividualTask : result.rows
            }
        }else{
            return{
                message: 'No Task Found' 
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function showGroupTask (tm){
    const {GroupID} = tm;
    const query = `SELECT * FROM TugasKelompok WHERE GroupID=${GroupID} ORDER BY tanggal_pengerjaan ASC`;
    const result = await db.query(query);
    if(result.rowCount > 0){
        return {
            message: 'Task Found',
            showGroupTask : result.rows
        }
    }else{
        return{
            message: 'No Task Found' 
        } 
    }

}

async function filterGroupTaskByStatus (tm){
    const {GroupID, Status} = tm;
    const query = `SELECT * FROM TugasKelompok WHERE GroupID=${GroupID} AND Status='${Status}' ORDER BY tanggal_pengerjaan ASC`;
    const result = await db.query(query);
    if(result.rowCount > 0){
        return {
            message: 'Task Found',
            showGroupTask : result.rows
        }
    }else{
        return{
            message: 'No Task Found' 
        } 
    }
    
}

async function searchGroupTaskByName (user){
    if(user){
        const query = `SELECT * FROM TugasKelompok WHERE GroupID=${user.GroupID} AND nama_tugas LIKE '%${user.Nama_Tugas}%' ORDER BY tanggal_pengerjaan ASC`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Task Found',
                showGroupTask : result.rows
            }
        }else{
            return{
                message: 'No Task Found' 
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function updateIndividualTask (tm, user){
    const {TugasIndividuID, Nama_Tugas, Deskripsi_tugas, Tanggal_pengerjaan, status} = tm;
    if(user){
        const query = `UPDATE TugasIndividu SET Nama_Tugas='${Nama_Tugas}', Deskripsi_Tugas='${Deskripsi_tugas}', Tanggal_Pengerjaan='${Tanggal_pengerjaan}', status='${status}' WHERE tugasIndividuID=${TugasIndividuID} AND UserID= ${user.UserID}`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: 'Task Updated'
            }
        }else{
            return{
                message: 'No Task Updated'
            } 
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function updateGroupTask (tm){
    const {GroupID, TugasGrupID, Nama_Tugas, Deskripsi_tugas, Tanggal_pengerjaan, status} = tm;
    const query = `UPDATE TugasKelompok SET Nama_Tugas='${Nama_Tugas}', Deskripsi_Tugas='${Deskripsi_tugas}', Tanggal_Pengerjaan='${Tanggal_pengerjaan}', status='${status}' WHERE tugasGrupID=${TugasGrupID} AND GroupID=${GroupID}`;
    const result = await db.query(query);
    if(result.rowCount > 0){
        return {
            message: 'Group Task Updated'
        }
    }else{
        return{
            message: 'No Task Updated'
        } 
    }
}

async function deleteIndividualTask (tm, user){
    const {TugasIndividuID} = tm;
    if(user){
        const query = `DELETE FROM TugasIndividu WHERE TugasIndividuID=${TugasIndividuID} AND UserId=${user.UserID}`;
        const result = await db.query(query);
        if(result.rowCount > 0){
            return {
                message: `Individual Task Deleted`
            }
        }else{
            return{
                message: 'No Task Deleted'
            }
        }
    }
    else{
        return {
            message: 'User not logged in'
        }
    }
}

async function deleteGroupTask (tm){
    const {GroupID, TugasGrupID} = tm;
    const query = `DELETE FROM TugasKelompok WHERE TugasGrupID=${TugasGrupID} AND GroupID=${GroupID}`;
    const result = await db.query(query);
    if(result.rowCount > 0){
        return {
            message: `Group Task Deleted`
        }
    }else{
        return{
            message: 'No Task Deleted'
        }
    }
}


module.exports = {
    login,
    register,
    showUser,
    deleteUser,
    addGroup,
    showGroup,
    searchGroupByName,
    searchGroupByID,
    enrollGroup,
    unenrollGroup,
    showEnrollment,
    showGroupMembers,
    addIndividualTask,
    addGroupTask,
    showIndividualTask,
    searchIndividualTaskByName,
    filterIndividualTaskByStatus,
    showGroupTask,
    searchGroupTaskByName,
    filterGroupTaskByStatus,
    updateIndividualTask,
    updateGroupTask,
    deleteIndividualTask,
    deleteGroupTask
}