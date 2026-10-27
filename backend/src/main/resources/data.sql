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

INSERT INTO opportunities (name, lead_id, lead_name, amount, stage, created_at) VALUES
('Ravi Sharma - Deal', 1, 'Ravi Sharma', 35000, 'Negotiation', CURRENT_TIMESTAMP),
('Neha Gupta - Deal', 4, 'Neha Gupta', 30000, 'Proposal', CURRENT_TIMESTAMP),
('Bulk order - Amit Verma', 3, 'Amit Verma', 45000, 'Prospecting', CURRENT_TIMESTAMP),
('Festive hamper order', NULL, 'Walk-in customer', 20000, 'Won', CURRENT_TIMESTAMP),
('Showroom display order', 5, 'Karan Mehta', 15000, 'Lost', CURRENT_TIMESTAMP);

INSERT INTO campaigns (name, message, segment, status, sent_count, created_at) VALUES
('Diwali Offer for Hot Leads', 'Hi {name}, enjoy 20% off this Diwali at RetailMax!', 'HOT', 'Draft', 0, CURRENT_TIMESTAMP);

INSERT INTO contacts (lead_id, name, email, phone, source, lifetime_value, created_at) VALUES
(NULL, 'Anita Desai', 'anita@example.com', '9876500101', 'Referral', 52000, CURRENT_TIMESTAMP),
(NULL, 'Rohit Malhotra', 'rohit@example.com', '9876500102', 'Store Visit', 18000, CURRENT_TIMESTAMP);