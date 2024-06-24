const { Router } = require('express');
const router = Router();

const { profile, updateProfile } = require('../controllers/profile.controller');

router.post('/auth/profile', profile);
router.post('/auth/updateProfile', updateProfile);

module.exports = router;
