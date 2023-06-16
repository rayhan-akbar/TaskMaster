const passport = require('passport');
const express = require('express');
const router = express.Router();
const tmController = require('../controllers/tm.controllers');

//router.post('/login',passport.authenticate('local'), tmController.login);

router.post('/login', tmController.login);

router.post('/register', tmController.register);

router.get('/showuser', tmController.showUser);

router.delete('/deleteuser', tmController.deleteUser);

router.post('/addgroup', tmController.addGroup);

router.get('/showgroup', tmController.showGroup);

router.post('/searchgroupbyname', tmController.searchGroupByName);

router.get('/searchgroupbyid', tmController.searchGroupByID);

router.post('/enrollgroup', tmController.enrollGroup);

router.delete('/unenrollgroup', tmController.unenrollGroup);

router.post('/showenrollment', tmController.showEnrollment);

router.post('/addindividualtask', tmController.addIndividualTask);

router.post('/addgrouptask', tmController.addGroupTask);

router.post('/showindividualtask', tmController.showIndividualTask);

router.post('/searchindividualtask', tmController.searchIndividualTaskByName);

router.post('/filterindividualtask', tmController.filterIndividualTaskByStatus);

router.post('/showgrouptask', tmController.showGroupTask);

router.post('/searchgrouptask', tmController.searchGroupTaskByName);

router.post('/filtergrouptask', tmController.filterGroupTaskByStatus);

router.put('/updateindividualtask', tmController.updateIndividualTask);

router.put('/updategrouptask', tmController.updateGroupTask);

router.delete('/deleteindividualtask', tmController.deleteIndividualTask);

router.delete('/deletegrouptask', tmController.deleteGroupTask);

module.exports = router;