INSERT INTO goal(deadline, target_weight, id, user_id, description)
VALUES ('2025-12-31', 75.0, UNHEX(REPLACE(UUID(), '-', '')),
        (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1), 'Lose 5kg before the end of the year');

INSERT INTO muscle_group (id, name)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Chest'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Back'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Legs'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Arms');

INSERT INTO exercise (id, name, description, difficulty, equipment)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Bench Press', 'Chest exercise using barbell or dumbbells', 'Medium',
        'Barbell'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Squat', 'Compound leg exercise with barbell', 'Hard', 'Barbell'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Bicep Curl', 'Arm isolation exercise using dumbbells', 'Easy', 'Dumbbells'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Deadlift', 'Back and leg exercise using barbell', 'Hard', 'Barbell');

INSERT INTO exercise_muscle_group (exercise_id, muscle_group_id)
VALUES ((SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Chest' LIMIT 1)),
       ((SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Back' LIMIT 1)),
       ((SELECT id FROM exercise WHERE name = 'Squat' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Legs' LIMIT 1)),
       ((SELECT id FROM exercise WHERE name = 'Bicep Curl' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Arms' LIMIT 1)),
       ((SELECT id FROM exercise WHERE name = 'Deadlift' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Back' LIMIT 1)),
       ((SELECT id FROM exercise WHERE name = 'Deadlift' LIMIT 1),
        (SELECT id FROM muscle_group WHERE name = 'Legs' LIMIT 1));

INSERT INTO workout (id, name, description)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Full Body Routine', 'A general strength-focused full body workout'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Leg Day Routine', 'Focus on building lower body strength'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Arm Blast', 'High-rep upper arm hypertrophy workout');

INSERT INTO workout_session (id, user_id, workout_id, date, notes, status)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1), '2025-05-12', 'Felt strong today',
        'COMPLETED'),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1), '2025-06-10', 'Heavy squat sets', 'COMPLETED'),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1), '2025-06-11', 'Great pump today', 'SCHEDULED');

INSERT INTO workout_set (id, session_id, workout_id, exercise_id, reps, weight)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id
                                          FROM workout_session
                                          WHERE workout_id =
                                                (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1)
                                          ORDER BY date DESC
                                          LIMIT 1), (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1), 10, 80.0),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id
                                          FROM workout_session
                                          WHERE workout_id =
                                                (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1)
                                          ORDER BY date DESC
                                          LIMIT 1), (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Squat' LIMIT 1), 8, 100.0),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id
                                          FROM workout_session
                                          WHERE workout_id = (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1)
                                          ORDER BY date DESC
                                          LIMIT 1), (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Bicep Curl' LIMIT 1), 12, 15.0);


# INSERT INTO goal(deadline, target_weight, id, user_id, description) VALUES ('2025-12-31', 75.0, UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1), 'Lose 5kg before the end of the year');
# INSERT INTO muscle_group (id, name) VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Chest'), (UNHEX(REPLACE(UUID(), '-', '')), 'Back');
# INSERT INTO exercise (id, name, description, difficulty, equipment) VALUES (UNHEX(REPLACE(UUID(), '-', '')),'Bench Press', 'Chest exercise using barbell or dumbbells', 'Medium', 'Barbell');
# INSERT INTO exercise_muscle_group (exercise_id, muscle_group_id) VALUES ((SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1), (SELECT id FROM muscle_group WHERE name = 'Chest' LIMIT 1)), ((SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1), (SELECT id FROM muscle_group WHERE name = 'Back' LIMIT 1));
# INSERT INTO workout (id, name, description) VALUES (UNHEX(REPLACE(UUID(), '-', '')),'Full Body Routine','A general strength-focused full body workout');
# INSERT INTO workout_session (id, user_id, workout_id, date, notes, status) VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1), (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1),'2025-05-12', 'Felt strong today', 'COMPLETED');
# INSERT INTO workout_set (id, session_id, workout_id, exercise_id, reps, weight) VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM workout_session WHERE user_id = (SELECT id FROM user WHERE email = 'admin@email.com' LIMIT 1) ORDER BY date DESC LIMIT 1), (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1), (SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1),10,80.0);
