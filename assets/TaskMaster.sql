--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: status; Type: TYPE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TYPE public.status AS ENUM (
    'NOT STARTED',
    'IN PROGRESS',
    'COMPLETED'
);


ALTER TYPE public.status OWNER TO rayhanakbararrizky;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: groupenrollment; Type: TABLE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TABLE public.groupenrollment (
    userid integer NOT NULL,
    groupid integer NOT NULL,
    tanggal_masuk date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.groupenrollment OWNER TO rayhanakbararrizky;

--
-- Name: groupenrollment_groupid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.groupenrollment_groupid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groupenrollment_groupid_seq OWNER TO rayhanakbararrizky;

--
-- Name: groupenrollment_groupid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.groupenrollment_groupid_seq OWNED BY public.groupenrollment.groupid;


--
-- Name: groupenrollment_userid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.groupenrollment_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groupenrollment_userid_seq OWNER TO rayhanakbararrizky;

--
-- Name: groupenrollment_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.groupenrollment_userid_seq OWNED BY public.groupenrollment.userid;


--
-- Name: groups; Type: TABLE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TABLE public.groups (
    groupid integer NOT NULL,
    nama_group character varying(20) NOT NULL,
    deskripsi text NOT NULL
);


ALTER TABLE public.groups OWNER TO rayhanakbararrizky;

--
-- Name: groups_groupid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.groups_groupid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groups_groupid_seq OWNER TO rayhanakbararrizky;

--
-- Name: groups_groupid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.groups_groupid_seq OWNED BY public.groups.groupid;


--
-- Name: tugasindividu; Type: TABLE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TABLE public.tugasindividu (
    userid integer NOT NULL,
    tugasindividuid integer NOT NULL,
    nama_tugas text NOT NULL,
    deskripsi_tugas text NOT NULL,
    tanggal_pengerjaan date NOT NULL,
    status public.status DEFAULT 'NOT STARTED'::public.status NOT NULL
);


ALTER TABLE public.tugasindividu OWNER TO rayhanakbararrizky;

--
-- Name: tugasindividu_tugasindividuid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.tugasindividu_tugasindividuid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tugasindividu_tugasindividuid_seq OWNER TO rayhanakbararrizky;

--
-- Name: tugasindividu_tugasindividuid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.tugasindividu_tugasindividuid_seq OWNED BY public.tugasindividu.tugasindividuid;


--
-- Name: tugasindividu_userid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.tugasindividu_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tugasindividu_userid_seq OWNER TO rayhanakbararrizky;

--
-- Name: tugasindividu_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.tugasindividu_userid_seq OWNED BY public.tugasindividu.userid;


--
-- Name: tugaskelompok; Type: TABLE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TABLE public.tugaskelompok (
    groupid integer NOT NULL,
    tugasgrupid integer NOT NULL,
    nama_tugas text NOT NULL,
    deskripsi_tugas text NOT NULL,
    tanggal_pengerjaan date NOT NULL,
    status public.status DEFAULT 'NOT STARTED'::public.status NOT NULL
);


ALTER TABLE public.tugaskelompok OWNER TO rayhanakbararrizky;

--
-- Name: tugaskelompok_groupid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.tugaskelompok_groupid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tugaskelompok_groupid_seq OWNER TO rayhanakbararrizky;

--
-- Name: tugaskelompok_groupid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.tugaskelompok_groupid_seq OWNED BY public.tugaskelompok.groupid;


--
-- Name: tugaskelompok_tugasgrupid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.tugaskelompok_tugasgrupid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tugaskelompok_tugasgrupid_seq OWNER TO rayhanakbararrizky;

--
-- Name: tugaskelompok_tugasgrupid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.tugaskelompok_tugasgrupid_seq OWNED BY public.tugaskelompok.tugasgrupid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: rayhanakbararrizky
--

CREATE TABLE public.users (
    userid integer NOT NULL,
    username character varying(20) NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.users OWNER TO rayhanakbararrizky;

--
-- Name: users_userid_seq; Type: SEQUENCE; Schema: public; Owner: rayhanakbararrizky
--

CREATE SEQUENCE public.users_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_userid_seq OWNER TO rayhanakbararrizky;

--
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rayhanakbararrizky
--

ALTER SEQUENCE public.users_userid_seq OWNED BY public.users.userid;


--
-- Name: groupenrollment userid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groupenrollment ALTER COLUMN userid SET DEFAULT nextval('public.groupenrollment_userid_seq'::regclass);


--
-- Name: groupenrollment groupid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groupenrollment ALTER COLUMN groupid SET DEFAULT nextval('public.groupenrollment_groupid_seq'::regclass);


--
-- Name: groups groupid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groups ALTER COLUMN groupid SET DEFAULT nextval('public.groups_groupid_seq'::regclass);


--
-- Name: tugasindividu userid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugasindividu ALTER COLUMN userid SET DEFAULT nextval('public.tugasindividu_userid_seq'::regclass);


--
-- Name: tugasindividu tugasindividuid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugasindividu ALTER COLUMN tugasindividuid SET DEFAULT nextval('public.tugasindividu_tugasindividuid_seq'::regclass);


--
-- Name: tugaskelompok groupid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugaskelompok ALTER COLUMN groupid SET DEFAULT nextval('public.tugaskelompok_groupid_seq'::regclass);


--
-- Name: tugaskelompok tugasgrupid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugaskelompok ALTER COLUMN tugasgrupid SET DEFAULT nextval('public.tugaskelompok_tugasgrupid_seq'::regclass);


--
-- Name: users userid; Type: DEFAULT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.users ALTER COLUMN userid SET DEFAULT nextval('public.users_userid_seq'::regclass);


--
-- Data for Name: groupenrollment; Type: TABLE DATA; Schema: public; Owner: rayhanakbararrizky
--

COPY public.groupenrollment (userid, groupid, tanggal_masuk) FROM stdin;
17	10	2023-06-01
17	11	2023-06-01
18	11	2023-06-01
18	13	2023-06-01
19	10	2023-06-05
19	17	2023-06-14
19	11	2023-06-16
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: rayhanakbararrizky
--

COPY public.groups (groupid, nama_group, deskripsi) FROM stdin;
10	Grup1	nyoba1
11	Grup2	nyoba2
13	Grup3	nyoba3
14	Group4	Nyoba4
15	Grup5	Nyoba5
16	Grup6	Nyoba6
17	Kelompok ALJUT	buat proyek akhir
\.


--
-- Data for Name: tugasindividu; Type: TABLE DATA; Schema: public; Owner: rayhanakbararrizky
--

COPY public.tugasindividu (userid, tugasindividuid, nama_tugas, deskripsi_tugas, tanggal_pengerjaan, status) FROM stdin;
19	16	Paper Algo	bikin paper	2023-06-20	NOT STARTED
19	17	Video SSF	bikin video	2024-06-14	NOT STARTED
19	18	UAS Matek	bikin citsit	2023-06-16	COMPLETED
19	15	Paper DMJ	buat paper review	2023-06-23	IN PROGRESS
19	21	Tes3	Tes3	2023-06-16	NOT STARTED
19	22	Tes4	Tes4	2023-06-23	NOT STARTED
19	23	Tes5	Tes5	2023-06-29	NOT STARTED
19	24	tes10	tes10	2023-06-29	NOT STARTED
19	25	Demo Proyek	Demonstrasi	2023-06-20	NOT STARTED
\.


--
-- Data for Name: tugaskelompok; Type: TABLE DATA; Schema: public; Owner: rayhanakbararrizky
--

COPY public.tugaskelompok (groupid, tugasgrupid, nama_tugas, deskripsi_tugas, tanggal_pengerjaan, status) FROM stdin;
10	5	TesUpdate2	Membuat Aplikasi	2023-05-24	COMPLETED
10	7	ABC	abc	2023-06-19	NOT STARTED
10	6	TesUpdate3	Membuat Aplikasi	2023-05-25	IN PROGRESS
13	8	Tugas Kelompok	Proyek Akhir	2023-06-20	NOT STARTED
10	10	tes	tes	2023-06-22	NOT STARTED
17	11	Proyek Akhir	-	2023-06-21	NOT STARTED
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: rayhanakbararrizky
--

COPY public.users (userid, username, password) FROM stdin;
17	frontend	$2a$10$4AgTjRG3Ouq/H3xMzV/qw.dbQo78ffvJnsdtuUGexYjIQP6wpBosq
18	rayhan2	$2a$10$GH54qPpwx6rxC6TbBNJ/Zui0w1nUs/mz6lwnVYlzrDwcGTD/T9l4i
19	Rayhan Akbar	$2a$10$oi47ilA.YE4Vo.jO1ww8BeZwzxq2uaT/IjOpyc6DLvnVDouUcB/Ou
20	tes2	$2a$10$iT.bCmFEY2zgBRB0Ym0alec9FRpRufXJzrd4Mkb8nN6wJZelz.Jei
24	Reibar	$2a$10$TvCCrQu/yCRmL9MaLmMj7e6BvDP1d8zsPt3MFd6lyHonqCq8TiHUe
\.


--
-- Name: groupenrollment_groupid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.groupenrollment_groupid_seq', 1, false);


--
-- Name: groupenrollment_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.groupenrollment_userid_seq', 1, false);


--
-- Name: groups_groupid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.groups_groupid_seq', 17, true);


--
-- Name: tugasindividu_tugasindividuid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.tugasindividu_tugasindividuid_seq', 25, true);


--
-- Name: tugasindividu_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.tugasindividu_userid_seq', 1, false);


--
-- Name: tugaskelompok_groupid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.tugaskelompok_groupid_seq', 1, false);


--
-- Name: tugaskelompok_tugasgrupid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.tugaskelompok_tugasgrupid_seq', 11, true);


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: rayhanakbararrizky
--

SELECT pg_catalog.setval('public.users_userid_seq', 24, true);


--
-- Name: groups groups_nama_group_key; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_nama_group_key UNIQUE (nama_group);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (groupid);


--
-- Name: tugasindividu tugasindividu_pkey; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugasindividu
    ADD CONSTRAINT tugasindividu_pkey PRIMARY KEY (tugasindividuid);


--
-- Name: tugaskelompok tugaskelompok_pkey; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugaskelompok
    ADD CONSTRAINT tugaskelompok_pkey PRIMARY KEY (tugasgrupid);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: groupenrollment groupenrollment_groupid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groupenrollment
    ADD CONSTRAINT groupenrollment_groupid_fkey FOREIGN KEY (groupid) REFERENCES public.groups(groupid) ON DELETE CASCADE;


--
-- Name: groupenrollment groupenrollment_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.groupenrollment
    ADD CONSTRAINT groupenrollment_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(userid) ON DELETE CASCADE;


--
-- Name: tugasindividu tugasindividu_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugasindividu
    ADD CONSTRAINT tugasindividu_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: tugaskelompok tugaskelompok_groupid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rayhanakbararrizky
--

ALTER TABLE ONLY public.tugaskelompok
    ADD CONSTRAINT tugaskelompok_groupid_fkey FOREIGN KEY (groupid) REFERENCES public.groups(groupid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

