INSERT INTO leads (name, email, phone, source, status, score, created_at) VALUES
('Ravi Sharma','ravi@example.com','9876500001','Store Visit','Qualified',70,CURRENT_TIMESTAMP),
('Priya Singh','priya@example.com','9876500002','Website','New',30,CURRENT_TIMESTAMP),
('Amit Verma','amit@example.com','9876500003','Referral','Contacted',60,CURRENT_TIMESTAMP),
('Neha Gupta','neha@example.com','9876500004','Website','Qualified',60,CURRENT_TIMESTAMP),
('Karan Mehta','karan@example.com','9876500005','Store Visit','Lost',40,CURRENT_TIMESTAMP),
('Sneha Rao','sneha@example.com','9876500006','Referral','New',50,CURRENT_TIMESTAMP);

INSERT INTO lead_status_history (lead_id, old_status, new_status, changed_at) VALUES
(1, NULL, 'New', DATEADD('HOUR', -48, CURRENT_TIMESTAMP)),
(1, 'New', 'Contacted', DATEADD('HOUR', -24, CURRENT_TIMESTAMP)),
(1, 'Contacted', 'Qualified', DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(2, NULL, 'New', CURRENT_TIMESTAMP),
(3, NULL, 'Contacted', CURRENT_TIMESTAMP),
(4, NULL, 'Qualified', CURRENT_TIMESTAMP),
(5, NULL, 'Lost', CURRENT_TIMESTAMP),
(6, NULL, 'New', CURRENT_TIMESTAMP);