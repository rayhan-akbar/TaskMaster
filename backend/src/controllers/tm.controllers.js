const tmService = require('../services/tm.services');

async function login(req,res){
    try{
        const result = await tmService.login(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function register(req,res){
    try{
        const result = await tmService.register(req.body);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function showUser(req,res){
    try{
        const result = await tmService.showUser(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}


async function deleteUser(req,res){
    try{
        const result = await tmService.deleteUser(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function addGroup(req,res){
    try{
        const result = await tmService.addGroup(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function showGroup(req,res){
    try{
        const result = await tmService.showGroup();
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function searchGroupByName(req,res){
    try{
        const result = await tmService.searchGroupByName(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function searchGroupByID(req,res){
    try{
        const result = await tmService.searchGroupByID(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function enrollGroup(req,res){
    try{
        const result = await tmService.enrollGroup(req.body, req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function unenrollGroup(req,res){
    try{
        const result = await tmService.unenrollGroup(req.body, req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function showEnrollment(req,res){
    try{
        const result = await tmService.showEnrollment(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function showGroupMembers(req,res){
    try{
        const result = await tmService.showGroupMembers(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function addIndividualTask(req,res){
    try{
        const result = await tmService.addIndividualTask(req.body, req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function addGroupTask(req,res){
    try{
        const result = await tmService.addGroupTask(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function showIndividualTask(req,res){
    try{
        const result = await tmService.showIndividualTask(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function searchIndividualTaskByName(req,res){
    try{
        const result = await tmService.searchIndividualTaskByName(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function filterIndividualTaskByStatus(req,res){
    try{
        const result = await tmService.filterIndividualTaskByStatus(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function showGroupTask(req,res){
    try{
        const result = await tmService.showGroupTask(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function searchGroupTaskByName(req,res){
    try{
        const result = await tmService.searchGroupTaskByName(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function filterGroupTaskByStatus(req,res){
    try{
        const result = await tmService.filterGroupTaskByStatus(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function updateIndividualTask(req,res){
    try{
        const result = await tmService.updateIndividualTask(req.body, req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function updateGroupTask(req,res){
    try{
        const result = await tmService.updateGroupTask(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function deleteIndividualTask(req,res){
    try{
        const result = await tmService.deleteIndividualTask(req.body, req.body);
        res.json(result);
    }catch(err){
        res.json(err);
    }
}

async function deleteGroupTask(req,res){
    try{
        const result = await tmService.deleteGroupTask(req.body);
        res.json(result);
    }catch(err){
        res.json(err);
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
    searchGroupTaskByName,
    searchIndividualTaskByName,
    filterIndividualTaskByStatus,
    showGroupTask,
    filterGroupTaskByStatus,
    updateIndividualTask,
    updateGroupTask,
    deleteIndividualTask,
    deleteGroupTask
}