import { CourseList } from "./course-list";
import React from 'react';
import Search from "./search";




export const Home = ({state, courses, handleChange, handleSearch}) => {
    return (
        <>
         <Search state={state} onSearchChange={handleChange} onSearch={handleSearch} />
         {courses && courses.length > 0 && (<div><CourseList data={courses} /></div>)} 
        </>
    )

}