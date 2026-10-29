-- ===== LEADS (18) =====
INSERT INTO leads (name, email, phone, source, status, score, created_at) VALUES
('Ravi Sharma','ravi@example.com','9876500001','Store Visit','Qualified',70,CURRENT_TIMESTAMP),
('Priya Singh','priya@example.com','9876500002','Website','New',30,CURRENT_TIMESTAMP),
('Amit Verma','amit@example.com','9876500003','Referral','Contacted',60,CURRENT_TIMESTAMP),
('Neha Gupta','neha@example.com','9876500004','Website','Qualified',60,CURRENT_TIMESTAMP),
('Karan Mehta','karan@example.com','9876500005','Store Visit','Lost',40,CURRENT_TIMESTAMP),
('Sneha Rao','sneha@example.com','9876500006','Referral','New',50,CURRENT_TIMESTAMP),
('Arjun Nair','arjun@example.com','9876500007','Website','Contacted',40,CURRENT_TIMESTAMP),
('Divya Iyer','divya@example.com','9876500008','Referral','Qualified',80,CURRENT_TIMESTAMP),
('Manish Kapoor','manish@example.com','9876500009','Website','New',30,CURRENT_TIMESTAMP),
('Pooja Bansal','pooja@example.com','9876500010','Website','Lost',30,CURRENT_TIMESTAMP),
('Vikram Singh','vikram@example.com','9876500011','Referral','Contacted',60,CURRENT_TIMESTAMP),
('Meera Joshi','meera@example.com','9876500012','Store Visit','Qualified',70,CURRENT_TIMESTAMP),
('Rahul Khanna','rahul@example.com','9876500013','Website','New',30,CURRENT_TIMESTAMP),
('Isha Agarwal','isha@example.com','9876500014','Referral','Lost',50,CURRENT_TIMESTAMP),
('Saurabh Tiwari','saurabh@example.com','9876500015','Store Visit','Contacted',50,CURRENT_TIMESTAMP),
('Kavya Reddy','kavya@example.com','9876500016','Website','Qualified',60,CURRENT_TIMESTAMP),
('Nitin Gupta','nitin@example.com','9876500017','Referral','New',50,CURRENT_TIMESTAMP),
('Anjali Mishra','anjali@example.com','9876500018','Store Visit','Contacted',50,CURRENT_TIMESTAMP);

-- ===== STATUS HISTORY =====
INSERT INTO lead_status_history (lead_id, old_status, new_status, changed_at) VALUES
(1, NULL, 'New', DATEADD('HOUR', -48, CURRENT_TIMESTAMP)),
(1, 'New', 'Contacted', DATEADD('HOUR', -24, CURRENT_TIMESTAMP)),
(1, 'Contacted', 'Qualified', DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(2, NULL, 'New', CURRENT_TIMESTAMP),
(3, NULL, 'New', DATEADD('HOUR', -30, CURRENT_TIMESTAMP)),
(3, 'New', 'Contacted', DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
(4, NULL, 'New', DATEADD('HOUR', -20, CURRENT_TIMESTAMP)),
(4, 'New', 'Qualified', DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
(5, NULL, 'New', DATEADD('HOUR', -40, CURRENT_TIMESTAMP)),
(5, 'New', 'Lost', DATEADD('HOUR', -10, CURRENT_TIMESTAMP)),
(6, NULL, 'New', CURRENT_TIMESTAMP),
(7, NULL, 'New', DATEADD('HOUR', -12, CURRENT_TIMESTAMP)),
(7, 'New', 'Contacted', DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
(8, NULL, 'New', DATEADD('HOUR', -72, CURRENT_TIMESTAMP)),
(8, 'New', 'Contacted', DATEADD('HOUR', -48, CURRENT_TIMESTAMP)),
(8, 'Contacted', 'Qualified', DATEADD('HOUR', -5, CURRENT_TIMESTAMP)),
(9, NULL, 'New', CURRENT_TIMESTAMP),
(10, NULL, 'New', DATEADD('HOUR', -60, CURRENT_TIMESTAMP)),
(10, 'New', 'Lost', DATEADD('HOUR', -20, CURRENT_TIMESTAMP)),
(11, NULL, 'New', DATEADD('HOUR', -26, CURRENT_TIMESTAMP)),
(11, 'New', 'Contacted', DATEADD('HOUR', -4, CURRENT_TIMESTAMP)),
(12, NULL, 'New', DATEADD('HOUR', -50, CURRENT_TIMESTAMP)),
(12, 'New', 'Contacted', DATEADD('HOUR', -30, CURRENT_TIMESTAMP)),
(12, 'Contacted', 'Qualified', DATEADD('HOUR', -8, CURRENT_TIMESTAMP)),
(13, NULL, 'New', CURRENT_TIMESTAMP),
(14, NULL, 'New', DATEADD('HOUR', -36, CURRENT_TIMESTAMP)),
(14, 'New', 'Lost', DATEADD('HOUR', -12, CURRENT_TIMESTAMP)),
(15, NULL, 'New', DATEADD('HOUR', -18, CURRENT_TIMESTAMP)),
(15, 'New', 'Contacted', DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(16, NULL, 'New', DATEADD('HOUR', -28, CURRENT_TIMESTAMP)),
(16, 'New', 'Qualified', DATEADD('HOUR', -7, CURRENT_TIMESTAMP)),
(17, NULL, 'New', CURRENT_TIMESTAMP),
(18, NULL, 'New', DATEADD('HOUR', -15, CURRENT_TIMESTAMP)),
(18, 'New', 'Contacted', DATEADD('HOUR', -1, CURRENT_TIMESTAMP));

-- ===== PIPELINE DEALS =====
INSERT INTO opportunities (name, lead_id, lead_name, amount, stage, created_at) VALUES
('Ravi Sharma - Deal', 1, 'Ravi Sharma', 35000, 'Negotiation', CURRENT_TIMESTAMP),
('Neha Gupta - Deal', 4, 'Neha Gupta', 30000, 'Proposal', CURRENT_TIMESTAMP),
('Divya Iyer - Deal', 8, 'Divya Iyer', 40000, 'Proposal', CURRENT_TIMESTAMP),
('Meera Joshi - Deal', 12, 'Meera Joshi', 35000, 'Prospecting', CURRENT_TIMESTAMP),
('Kavya Reddy - Deal', 16, 'Kavya Reddy', 30000, 'Prospecting', CURRENT_TIMESTAMP),
('Bulk order - Amit Verma', 3, 'Amit Verma', 45000, 'Prospecting', CURRENT_TIMESTAMP),
('Festive hamper order', NULL, 'Walk-in customer', 20000, 'Won', CURRENT_TIMESTAMP),
('Corporate gifting order', NULL, 'Anita Desai', 52000, 'Won', CURRENT_TIMESTAMP),
('Showroom display order', 5, 'Karan Mehta', 15000, 'Lost', CURRENT_TIMESTAMP);

-- ===== CAMPAIGNS =====
INSERT INTO campaigns (name, message, segment, status, sent_count, created_at) VALUES
('Diwali Offer for Hot Leads', 'Hi {name}, enjoy 20% off this Diwali at RetailMax!', 'HOT', 'Draft', 0, CURRENT_TIMESTAMP),
('New Arrivals for Referral Leads', 'Hi {name}, our new festive collection is in store. Thank you for being referred to us!', 'REFERRAL', 'Draft', 0, CURRENT_TIMESTAMP);

-- ===== CUSTOMERS =====
INSERT INTO contacts (lead_id, name, email, phone, source, lifetime_value, created_at) VALUES
(NULL, 'Anita Desai', 'anita@example.com', '9876500101', 'Referral', 52000, CURRENT_TIMESTAMP),
(NULL, 'Rohit Malhotra', 'rohit@example.com', '9876500102', 'Store Visit', 18000, CURRENT_TIMESTAMP);